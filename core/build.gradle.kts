import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id 'java'
}

group = 'dev.kscott'
version = '1.0.0'

sourceCompatibility = '1.8'
targetCompatibility = '1.8'

repositories {
    mavenCentral()
    maven {
        name = 'papermc-repo'
        url = 'https://papermc.io/repo/repository/maven-public/'
    }
    maven {
        name = 'sonatype'
        url = 'https://oss.sonatype.org/content/groups/public/'
    }
}

dependencies {
    compileOnly 'com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT'
}

processResources {
    from(sourceSets.main.resources.srcDirs) {
        filter ReplaceTokens, tokens: [version: version]
    }
}
