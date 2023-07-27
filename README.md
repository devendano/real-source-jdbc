# Read me first

before running the application configure the environment:

### Create table

```roomsql
CREATE TABLE public.tutorials (
	t_id bigserial PRIMARY KEY NOT NULL,
	t_title varchar(255),
	t_description varchar(255),
	is_published bool
);
```

### Insert Values

```roomsql
INSERT INTO public.tutorials (t_title, t_description, is_published)
VALUES
('Spring tutorial', 'Basic tutorial with the spring framework', false),
('JAVA tutorial', 'Tutorial of the main java functions', true),
('Maven tutorial', 'Learn Maven from Scratch', true),
('Junit 4 tutorial', 'Make your tests with Junit 4', true);
```

### Configure data source
for example:

```properties
spring.datasource.url= jdbc:postgresql://localhost:5432/postgres
spring.datasource.username= postgres
spring.datasource.password= 2079
```