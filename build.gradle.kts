plugins {
    id("com.gradleup.nmcp.aggregation") version "1.6.2"
}

group = "me.xkyrell"
version = "2.0"

dependencies {
    allprojects {
        nmcpAggregation(project(path))
    }
}

nmcpAggregation {
    centralPortal {
        username = System.getenv("mavenCentralUsername")
        password = System.getenv("mavenCentralPassword")
        publishingType = "USER_MANAGED"
        publicationName = "kstemporal:$version"
    }
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
