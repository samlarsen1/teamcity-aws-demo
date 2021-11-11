# TeamCity docker-compose samples

Sourced from here: https://github.com/JetBrains/teamcity-docker-samples

Once started up get super user login token with:

```shell script
grep -E 'token\: [0-9]{16,22}' teamcity-server-logs/teamcity-server.log | tail -1
```