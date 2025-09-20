// Init script to force a single JavaPoet version across all configurations
// Put this under the repository's gradle/init.d so Gradle will apply it for builds

allprojects {
    configurations.configureEach {
        resolutionStrategy {
            // Force the known-good javapoet version
            force("com.squareup:javapoet:1.13.0")

            // Also ensure any requested javapoet version uses 1.13.0
            eachDependency {
                if (requested.group == "com.squareup" && requested.name == "javapoet") {
                    useVersion("1.13.0")
                }
            }
        }
    }
}
