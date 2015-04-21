package lt.gw.freebug.logez

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix="smtp")
class Smtp {
	String host
}
