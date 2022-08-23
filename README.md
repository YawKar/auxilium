# Auxilium
Anonymous p2p mutual assistance service inside the telegram bot

![Project stage: Development][project-stage-badge: Development]

[project-stage-badge: Development]: https://img.shields.io/badge/Project%20Stage-Development-yellowgreen.svg

## Table of Contents
* [How to run](#how-to-run)
* [User contexts](#user-contexts)
* [Database entities](#database-entities)
* [Technologies and Features used](#technologies-and-features-used)

## How to run
```bash
chmod +x mvnw                     # allow executing maven wrapper
./mvnw clean                      # clean /target (if it's not the first run)
./mvnw package -Dmaven.test.skip  # build .jar (and skip testing)
docker compose up                 # run composition
```

## User contexts
### Unknown context
| Command  | Description                                            |
|----------|--------------------------------------------------------|
| `/start` | starts the interaction with bot (`-> Passive context`) |
### Passive context
| Command      | Description                                                                  |
|--------------|------------------------------------------------------------------------------|
| `/need_help` | starts the process of finding the help-mate (`-> Finding help-mate context`) |
| `/open`      | registers user as a help-mate                                                |
| `/close`     | removes user from help-mates                                                 |
### Finding help-mate context
| Command      | Description                                                       |
|--------------|-------------------------------------------------------------------|
| `/stop_help` | stops the process of finding the help-mate (`-> Passive context`) |

### Active context
| Command        | Description                         |
|----------------|-------------------------------------|
| `/end_session` | ends session (`-> Passive context`) |

## Database entities
soon...

## Technologies and Features used

![java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![spring](https://img.shields.io/badge/spring%20-%236DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/static/v1?style=for-the-badge&message=Spring+Boot&color=6DB33F&logo=Spring+Boot&logoColor=FFFFFF&label=)
![Spring Data](https://img.shields.io/static/v1?style=for-the-badge&message=Spring+Data&color=6DB33F&logo=Spring+Data&logoColor=FFFFFF&label=)
![maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven)
![postgres](https://img.shields.io/badge/postgres-%23316192.svg?&style=for-the-badge&logo=postgresql&logoColor=white)
![docker](https://img.shields.io/badge/docker-%232496ED.svg?&style=for-the-badge&logo=docker&logoColor=white)
![Telegram](https://img.shields.io/badge/Telegram-2CA5E0?style=for-the-badge&logo=telegram&logoColor=white)

* Spring
    * Spring Boot 2.7.1
    * Spring Data JPA
* PostgreSQL 14
* [rubenlagus/TelegramBots](https://github.com/rubenlagus/TelegramBots)
* Lombok
* Docker
    * docker-compose (PostgreSQL14 + Spring Boot)
