# ТЗ: Услуги филиала, кастомные услуги мастера, привязки мастера к филиалам

**Версия:** 1.0
**Статус:** готово к разработке
**Скоуп:** модель данных + API + правила видимости + автоотмена броней
**Вне скоупа:** UI бронирования, отправка уведомлений, отчёты

---

## Содержание

1. [Скоуп](#1-скоуп)
2. [Термины](#2-термины)
3. [Модель данных](#3-модель-данных)
4. [Бизнес-правила](#4-бизнес-правила)
5. [API](#5-api)
6. [Ошибки](#6-ошибки)
7. [Критерии приёмки](#7-критерии-приёмки)
8. [Отложено](#8-отложено)

---

## 1. Скоуп

**Реализуем:**
- каталог дефолтных услуг филиала;
- кастомные услуги мастера на основе дефолтных (переопределение цены и длительности);
- привязку мастера к филиалам (many-to-many);
- видимость услуг и мастеров для клиента;
- автоотмену будущих броней при уходе мастера из филиала.

**Не реализуем (но закладываем точки расширения):**
- UI бронирования;
- отправка уведомлений (только доменное событие);
- отчёты.

---

## 2. Термины

| Термин | Значение |
|---|---|
| **Branch** | Филиал, у него свой прайс |
| **DefaultService** | Дефолтная услуга филиала. «Витрина» |
| **Master** | Сотрудник |
| **MasterBranch** | Привязка мастера к филиалу (many-to-many), с `is_primary` и `active` |
| **MasterService** | Кастомная услуга мастера на основе дефолтной. Хранит свою цену и длительность |

---

## 3. Модель данных

### `branch`
- `id` — PK
- `name`
- `active`

### `default_service`
- `id` — PK
- `branch_id` — FK → `branch.id`
- `name`
- `base_price`
- `base_duration_minutes`
- `active`

### `master`
- `id` — PK
- `name`
- `active`
- ⚠️ **`branch_id` отсутствует.** Филиалы мастера — только через `master_branch`

### `master_branch` 🆕
- `id` — PK
- `master_id` — FK → `master.id`
- `branch_id` — FK → `branch.id`
- `is_primary` — boolean, default `false`
- `active` — boolean, default `true`
- `valid_from` — date, nullable
- `valid_to` — date, nullable
- `created_at`, `updated_at`

**Ограничения:**
- `UNIQUE (master_id, branch_id)`
- **Частичный unique index:** `UNIQUE (master_id) WHERE is_primary = true`
- `valid_from <= valid_to`, если оба заданы

### `master_service`
- `id` — PK
- `master_id` — FK → `master.id`
- `default_service_id` — FK → `default_service.id`
- `price`
- `duration_minutes`
- `created_at`, `updated_at`

**Ограничения:**
- `UNIQUE (master_id, default_service_id)`
- `price > 0`
- `duration_minutes > 0`
- ⚠️ **`branch_id` отсутствует** (филиал выводится через `default_service.branch_id`)
- ⚠️ **`active` отсутствует** (видимость — вычисляемая, см. BR-9)

### `booking` (в скоупе только в части отмены)
- `id` — PK
- `master_id` — FK
- `branch_id` — FK
- `master_service_id` — FK
- `start_at`
- `status` — `PENDING` / `CONFIRMED` / `CANCELLED` / `COMPLETED`
- `cancelled_at` — nullable
- `cancel_reason` — enum, nullable (`MASTER_LEFT_BRANCH`)
- **snapshot-поля:** `price`, `duration_minutes` (фиксируются при создании)

### Hibernate-связи
- `MasterBranch @ManyToOne Master`
- `MasterBranch @ManyToOne Branch`
- `MasterService @ManyToOne Master`
- `MasterService @ManyToOne DefaultService`
- `DefaultService @ManyToOne Branch`

---

## 4. Бизнес-правила

### 4.1. Витрина и каталог

| ID | Правило |
|---|---|
| **BR-1** | Клиенту показываются только активные `default_service` выбранного филиала |
| **BR-9** | `master_service` видна клиенту, только если: `default_service.active = true` **И** `master.active = true` **И** существует `master_branch` с `active = true` для `(master_id, default_service.branch_id)`. Видимость — **вычисляемая**, отдельного поля нет |
| **BR-12** | Реактивация `default_service` автоматически возвращает связанные `master_service` в каталог — ручных действий не требуется |

### 4.2. Создание и редактирование `master_service`

| ID | Правило |
|---|---|
| **BR-2** | Мастер может создать `master_service` только на `default_service`, чей филиал входит в активные привязки мастера. Иначе → `400` |
| **BR-7** | Нельзя создать две `master_service` с одинаковыми `(master_id, default_service_id)` → `409` |
| **BR-10** | Нельзя создать `master_service` на неактивную `default_service` → `400` |
| **BR-14** | Обновление `price` / `duration_minutes` в скрытой `master_service` разрешено |

### 4.3. Snapshot цены/длительности

| ID | Правило |
|---|---|
| **BR-3** | Изменение `default_service.base_price` / `base_duration_minutes` не меняет `master_service.price` / `duration_minutes` |
| **BR-4** | Мастер/владелец может вручную обновить `master_service.price` и `duration_minutes` |
| **BR-5** | При создании брони цена и длительность берутся из `master_service` и **сохраняются в брони snapshot’ом** |

### 4.4. Отображение мастеров

| ID | Правило |
|---|---|
| **BR-6** | Клиент видит мастера для выбранной услуги, только если: мастер активен **И** есть активная `master_branch` для филиала этой услуги **И** у мастера есть `master_service` на эту услугу |
| **BR-29** | `is_primary` на видимость и бронирование не влияет. Только для отчётов/UI/сортировок |

### 4.5. `master_branch`

| ID | Правило |
|---|---|
| **BR-24** | У мастера не более одной `master_branch` с `is_primary = true` |
| **BR-25** | Первая привязка мастера создаётся с `is_primary = true` автоматически |
| **BR-26** | Смена основной привязки (`PATCH { isPrimary: true }`) атомарно снимает флаг со всех остальных привязок мастера |
| **BR-28** | ⚠️ **Жёсткое правило:** нельзя остаться без основной привязки.<br>• `PATCH { isPrimary: false }` на единственной основной → `400`<br>• `PATCH { active: false }` на основной → `400`, если у мастера нет другой активной привязки. В этом случае клиент обязан передать `newPrimaryBranchId` в том же запросе |
| **BR-15** | «Мастер временно в другом филиале» = создать/активировать `master_branch` для нового филиала. Возврат = `active = false` для этого филиала. `master_service` не удаляются и при повторной активации снова доступны |

### 4.6. Автоотмена броней при уходе мастера

| ID | Правило |
|---|---|
| **BR-18** | При `PATCH /master-branches/{id} { active: false }` **автоматически отменяются** брони, у которых: `master_id` совпадает **И** `branch_id` совпадает **И** `start_at > now()` **И** `status IN (PENDING, CONFIRMED)` |
| **BR-19** | Отмена фиксирует: `status = CANCELLED`, `cancelled_at = now()`, `cancel_reason = 'MASTER_LEFT_BRANCH'` |
| **BR-20** | Операция атомарна: деактивация привязки + отмена броней в одной транзакции. Ошибка → полный откат |
| **BR-21** | Реактивация привязки не восстанавливает отменённые брони |
| **BR-22** | Отмена генерирует доменное событие `BookingCancelled(reason = MASTER_LEFT_BRANCH)`. Нотификации не отправляем — задача отложена |
| **BR-23** | Отмена синхронная, не через планировщик |

### 4.7. Бронирование (валидации, без реализации UI)

| ID | Правило |
|---|---|
| **BR-30** | `POST /bookings` принимает **`masterServiceId`** и `startAt`. Всё остальное (цена, длительность, филиал, мастер) выводится сервером. Клиент **не** присылает цену/длительность |
| **BR-31** | `POST /bookings` валидирует видимость по BR-9. Скрытая услуга → `409` |
| **BR-32** | Ответ содержит snapshot: `price`, `durationMinutes`, `branchId`, `masterId`, `defaultServiceName` |

---

## 5. API

### 5.1. Каталог (клиент)

| Метод | Путь | Описание |
|---|---|---|
| `GET` | `/branches/{branchId}/services` | Активные `default_service` филиала |
| `GET` | `/branches/{branchId}/services/{defaultServiceId}/masters` | Активные мастера филиала с настроенной `master_service` на эту услугу (BR-6 + BR-9) |
| `GET` | `/masters/{masterId}/branches` | **Только активные** привязки мастера |
| `GET` | `/masters/{masterId}/services?branchId=...` | **Только видимые** кастомные услуги (BR-9). `branchId` опционален. Без флага `visible` |
| `POST` | `/bookings` | Body: `{ masterServiceId, startAt }` |

### 5.2. Админка / мастер

| Метод | Путь | Описание |
|---|---|---|
| `GET` | `/admin/masters/{masterId}/services` | **Все** `master_service`, включая скрытые, с флагом `visible: boolean` |
| `POST` | `/masters/{masterId}/services` | Body: `{ defaultServiceId, price, durationMinutes }`. Валидации: BR-2, BR-7, BR-10 |
| `PATCH` | `/master-services/{id}` | Body: `{ price?, durationMinutes? }` |
| `GET` | `/admin/masters/{masterId}/branches` | Все привязки, включая неактивные |
| `POST` | `/masters/{masterId}/branches` | Body: `{ branchId, validFrom?, validTo? }`. Первая привязка → `is_primary = true` |
| `PATCH` | `/master-branches/{id}` | Body: `{ active?, isPrimary?, newPrimaryBranchId? }`.<br>• `active: false` → автоотмена броней (BR-18…BR-23) в одной транзакции<br>• `isPrimary: true` → атомарная смена основной (BR-26)<br>• `active: false` на основной → требует `newPrimaryBranchId` (BR-28) |

### 5.3. Разделение контекстов

⚠️ Админский и клиентский эндпоинты `/masters/{id}/services` — **разные пути** (`/admin/...` vs без префикса). Не смешиваем поведение по роли в одном эндпоинте.

---

## 6. Ошибки

| Ситуация | Код |
|---|---|
| `master_service` на чужой филиал | `400` |
| `master_service` на неактивную `default_service` | `400` |
| Дубликат `(master_id, default_service_id)` | `409` |
| `price <= 0` или `duration_minutes <= 0` | `400` |
| Бронь на скрытую `master_service` | `409` |
| `isPrimary: false` на единственной основной | `400` |
| `active: false` на основной без `newPrimaryBranchId` и без другой активной привязки | `400` |

---

## 7. Критерии приёмки

1. Клиент видит только активные услуги из прайса филиала.
2. Мастер не может создать `master_service` на услугу чужого филиала.
3. `master_services` не содержит `branch_id`.
4. Изменение цены/длительности `default_service` не меняет `master_service`.
5. При записи используется `duration_minutes` из `master_service`.
6. Цена и длительность сохраняются в брони snapshot’ом.
7. Дубликат `(master_id, default_service_id)` → `409`.
8. Клиент видит для услуги только мастеров филиала с настроенной услугой.
9. `master_service` на неактивной `default_service` не видна клиенту, не даёт создать бронь, но редактируема мастером с `visible: false` в админке.
10. Реактивация `default_service` возвращает `master_service` в каталог без ручных действий.
11. `master.branch_id` отсутствует; филиалы — только через `master_branch`.
12. `master_branch.active = false` → мастер не виден в этом филиале и не может создавать новые брони.
13. `master_branch.active = false` → будущие `PENDING`/`CONFIRMED` брони мастера в этом филиале автоматически отменяются с `cancel_reason = MASTER_LEFT_BRANCH`. Атомарно с деактивацией.
14. Прошедшие и завершённые брони не отменяются.
15. Реактивация привязки не восстанавливает отменённые брони.
16. Ровно одна `master_branch.is_primary = true` на мастера (частичный unique index).
17. Первая привязка → `is_primary = true`.
18. Смена основной атомарно снимает флаг с прежней.
19. `active: false` на основной без `newPrimaryBranchId` (при наличии других активных) → `400`. Без других активных → `400`.
20. Клиентский `GET /masters/{id}/services` не отдаёт скрытые `master_service`.
21. `POST /bookings` валидирует BR-9 и возвращает snapshot.

---

## 8. Отложено

- 🔔 **Нотификации об автоотмене.** Сейчас только доменное событие `BookingCancelled(reason = MASTER_LEFT_BRANCH)`. Поднять вопрос позже — нужно решить, как и когда уведомляем клиента.
- 🖥️ UI бронирования (оба сценария: через филиал и через мастера).
- 📊 Отчёты с использованием `is_primary`.
- ⚙️ Если автоотмена станет тяжёлой при большом числе броней — вынести в фоновую задачу со статусом `DEACTIVATING`.

---

## Приложение A. Клиентские сценарии бронирования (для контекста API)

### Сценарий 1: «через филиал»
`branch → список услуг → выбор услуги → список мастеров → выбор мастера → бронь`

Эндпоинты: `/branches/{id}/services` → `/branches/{id}/services/{sid}/masters` → `POST /bookings`.

### Сценарий 2: «через мастера»
`мастер → список его услуг → выбор услуги → бронь`

Эндпоинты: `/masters/{id}/branches` → `/masters/{id}/services?branchId=...` → `POST /bookings`.

Оба сценария сходятся к единому `POST /bookings { masterServiceId, startAt }` (BR-30).