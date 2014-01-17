<!-- 0. If your database is mysql - its enough to complete following steps. If not - customise project for your needs manually (it's NOT sufficient to modify following steps accordingly) -->
<!-- 1. Create database dbvs_db -->
<!-- 2. Create database dbvs_db_test for running integration tests-->
<!-- 3. Create database user 'dbvs_user' identified by password 'dbvs_pass' -->
<!-- 4. Grant username 'dbvs_user' permissions to both databases: dbvs_db and dbvs_db_test -->

<Resource
    name="jdbc/dbvs_dbDataSource"
    url="jdbc:mysql://localhost:3306/dbvs_db?tinyInt1isBit=false&amp;characterEncoding=utf8"
    username="dbvs_user"
    password="dbvs_pass"
    driverClassName="com.mysql.jdbc.Driver"
    auth="Container"
    initialSize="5"
    maxActive="100"
    maxIdle="10"
    minEvictableIdleTimeMillis="600000"
    minIdle="0"
    timeBetweenEvictionRunsMillis="60000"
    type="javax.sql.DataSource"
    />

<!-- 5. Copy this <Resource> fragment to $CATALINA_HOME/conf/server.xml and put it inside <GlobalNamingResources> tag -->
<!-- 6. Copy following <ResourceLink> fragment to $CATALINA_HOME/conf/context.xml and put it inside Context tag -->

<ResourceLink
    global="jdbc/dbvs_dbDataSource"
    name="jdbc/dbvs_dbDataSource"
    type="javax.sql.DataSource"
    />

<!-- 7. Copy into $CATALINA_HOME/conf/catalina.properties -->

spring.profiles.active=production

<!-- 8. Put DB drivers jars into $CATALINA_HOME/lib folder. -->
<!-- 9. Restart Tomcat -->
