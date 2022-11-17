package quarkus.mservices.offerprice;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppInstanceInfo {
    @ConfigProperty(name = "MY_POD_NAME", defaultValue = "unknown-unknown-unknown-unknown-unknown") String podName;

    public String getInstanceInfo() {
        return podName.substring(podName.length()-16);
    }

}
