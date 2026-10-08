Release roadmap
v0.1.0 — Local deployment tracker
Goal: Prove the basic API works locally.
Already completed:
- Spring Boot starts.
- H2 database initializes.
- Create a deployment.
- List deployments.
- Retrieve a deployment by ID.
- Automatically assign deployedAt.
- Application context test passes.

Still needed:
- Add a README with setup instructions.
- Add controller tests for all three endpoints.
- Decide whether LocalDateTime should become Instant.
- Remove unused Lombok or start using it.



### v0.1.1 — GitHub Actions CI

Goal: Establish an automated CI workflow before continuing feature development.

Planned work:

- Add a GitHub Actions workflow.
- Run the workflow on pushes and pull requests.
- Set up Java 17 in the workflow environment.
- Build the project using the Maven Wrapper.
- Run all automated tests with `./mvnw test`.
- Fail the workflow when compilation or tests fail.
- Add the workflow status badge to this README.

Definition of done:

- Every pull request automatically runs the test suite.
- Every push to the main branch automatically runs the test suite.
- A failed build or test produces a failed GitHub check.
- A successful build produces a passing GitHub check.
- The workflow requires no database or credentials outside the repository.

v0.2.0 — Safe and validated API
Goal: Reject invalid data and return predictable errors.
Work items:
- Create Environment enum:
  - DEVELOPMENT
  - STAGING
  - PRODUCTION
- Create DeploymentStatus enum:
  - STARTED
  - SUCCESS
  - FAILED
- Require applicationName, environment, version, and status.
- Add request validation with @Valid.
- Return useful 400 Bad Request responses.
- Add tests for invalid requests.
- Prevent clients from manually supplying database-controlled fields such as id.

v0.3.0 — Useful deployment history
Goal: Make the stored information searchable.
Work items:
- Filter by application name.
- Filter by environment.
- Filter by status.
- Sort by deployedAt, newest first.
- Add pagination so the API doesn’t return unlimited records.
- Add a “latest deployment” endpoint.

v0.4.0 — Persistent PostgreSQL storage
Goal: Keep deployment history after application restarts.
Work items:
- Run PostgreSQL locally with Docker Compose.
- Add the PostgreSQL JDBC driver.
- Create separate local, test, and production configuration profiles.
- Continue using H2 for fast automated tests.
- Add Flyway database migrations.
- Create the deployment table through Flyway.
- Configure credentials with environment variables.
- Add repository integration tests.