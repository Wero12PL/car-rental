---
name: backend-guidelines
description: Use when reviewing, writing, or refactoring Java/Spring backend code. Enforces layered architecture, clean code, REST API conventions, database best practices, and testing standards for any Java/Spring project.
---

# Backend Guidelines

Comprehensive backend development guidelines for Java/Spring projects.

## Architecture

### Layered Architecture

```
Controller → Service → Repository
```

**Controller:**
- Communicates with service layer
- Invokes validation
- Invokes permission checks
- Converts user data ↔ domain (entity to bean)
- Returns/Receives Bean
- Handles error codes/status codes
- JSON mapping (convert bean to string and send)

**Service:**
- Communicates with other services or repositories
- Manual filtering/sorting (if not possible on DB level)
- Implements validation logic (separate service)
- Implements permission checks
- Business logic (operating and modifying data from repository/user)

**Repository:**
- Retrieves data from DB
- Builds database queries

### Package Structure

One package per domain:

```
domain/
  DomainController.java
  DomainService.java
  DomainPermissionService.java
  DomainValidationService.java
  DomainRepository.java
  Domain.java (entity)
  DomainBean.java (user data)
```

## Naming Conventions

### Classes
- Controllers: `*Controller` suffix
- Services: `*Service` suffix
- Repositories: `*Repository` suffix
- User data: `*Bean` suffix
- Use whole words, nouns, unique names
- No irrelevant context (project/company names)
- Single Responsibility Principle
- Clear and concise, proper spelling

### Functions
- Names reveal intention
- Names are verbs
- Names unique in given scope
- No irrelevant context (project/company names, types, class names)
- Split into smaller functions (one thing, max 2 indentations)
- Max 3 arguments
- No flag arguments
- Command Query Separation: either command (returns void) or query (returns data)

## REST API

### Resources
- Plural form: `/resources`, `/resources/{id}`
- Sub-resources: `/resources/{id}/sub-resources`
- Bulk actions: `/resources/bulk`
- Cloning: `/resources/clone`

### HTTP Status Codes
- 200 OK → get/update success
- 201 Created → post success
- 204 No Content → delete success
- 400 Bad Request → invalid data
- 403 Forbidden → permission denied
- 404 Not Found → element doesn't exist or no permission
- 409 Conflict → concurrent modification

### Updates
- Use PATCH for partial updates to minimize payload

## Code Style

### Best Practices
- Keep classes encapsulated
- Classes small, single responsibility
- Base class knows nothing about derivatives
- Access static via class name
- No magic numbers
- No duplicate code (DRY vs KISS)
- YAGNI: don't implement until necessary
- Boy scout rule: leave code better
- Look for root cause of problems
- Keep configurable data at high levels
- Consistent solutions across application
- Max 3-4 dependencies per class

### Declarations
- One declaration per line
- Declare variables close to usage
- No space between method name and parenthesis
- Open brace `{` at end of declaration line
- Closing brace `}` on own line, indented
- Methods separated by blank line

### Statements
- One statement per line
- Braces around all statements (if-else, for)
- Break after comma, before operator
- Prefer higher-level breaks
- 4 spaces indentation

### Files
- Max 500 lines
- Group methods by functionality, not scope
- Caller above callee
- Follow Java coding conventions file organization

## Comments

- Block comments after blank line to describe next section
- Don't comment out old code (use git history)
- Comment as explanation of intent (why solution was used)
- Comment as clarification (when hard to read)
- Comment as warning of consequences
- TODO only with ticket key, not for tech debt

## Exceptions

- Use runtime exceptions
- Handle with `@ControllerAdvice` exception handler
- Return appropriate status code + response text
- Meaningful message + stack trace logged
- Exception names match HTTP status codes
- Throw for validation, permission, serialization issues

## Logging

- Meaningful messages with all relevant context
- No log litter (repetition, unnecessary data)
- Log stack traces for errors
- Server logs read by customers: only crucial data
- No sensitive information
- Use string placeholders, not concatenation
- Use `log.isDebugEnabled()` for computations

## Database

### Migrations
- Use snake_case for column names
- No abbreviations/acronyms
- Define data types and constraints
- Normalize database (1NF, 2NF, 3NF)
- Add indexes on WHERE/JOIN/GROUP BY/ORDER BY columns
- Don't index SELECT columns
- Separate index per column
- No indexes on small tables
- Fewer indexes on heavily updated tables
- Unique indexes when data should be unique
- Don't add indexes in both Java code and migration

### Primary Key
- Non-null, comparable
- Use INT or BIGINT (faster comparisons)

### Hibernate Relationships
- Use bidirectional `@ManyToOne` over unidirectional `@OneToMany`
- Use `Set` for `@ManyToMany`, not `List`
- Add `FetchType.LAZY` to `@ManyToOne`
- Use `mappedBy` flag in `@OneToMany`

### N+1 Problem
- Avoid by designing shallow REST representations
- Use JOINs for multi-table data
- Don't use eager fetching unless safe

## Testing

- Unit tests for business logic (services, models)
- Adequate edge case coverage
- Integration tests for endpoints and DB queries
- Split test files if >300 lines
- Group related tests together

## Dependency Injection

- Use constructor injection (immutable, null-safe, easy to mock)
- No field injection

## Validation

- Validate before handling endpoint
- Check: empty strings, null, format, character limits
- URL params: validate types

## Permission Checks

```java
if (!permissionService.hasEditPermission()) {
    throw new NoEditPermissionException("You can't edit this element");
}
```

- Extract to permission service
- Consistent pattern across application
- Check request client key matches element client key
- Check browse/edit/create permissions
