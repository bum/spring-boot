package samples.websocket.jetty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import samples.websocket.jetty.echo.DefaultEchoService;
import samples.websocket.jetty.echo.EchoService;
import samples.websocket.jetty.echo.EchoWebSocketHandler;
import samples.websocket.jetty.reverse.ReverseWebSocketEndpoint;
import samples.websocket.jetty.snake.SnakeWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(echoWebSocketHandler(), "/echo").addInterceptors(httpSessionHandshakeInterceptor())
				.withSockJS();
		registry.addHandler(snakeWebSocketHandler(), "/snake").addInterceptors(httpSessionHandshakeInterceptor())
				.withSockJS();
	}

	@Bean
	public HandshakeInterceptor httpSessionHandshakeInterceptor() {
		return new HttpSessionHandshakeInterceptor();
	}

	// ============= SNAKE ============= //
	@Bean
	public WebSocketHandler snakeWebSocketHandler() {
		return new PerConnectionWebSocketHandler(SnakeWebSocketHandler.class);
	}

	// ============= ECHO ============= //
	@Bean
	public WebSocketHandler echoWebSocketHandler() {
		return new EchoWebSocketHandler(echoService());
	}

	@Bean
	public EchoService echoService() {
		return new DefaultEchoService("Did you say \"%s\"?");
	}

	// ============= ENDPOINT ============= //
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Bean
	public ReverseWebSocketEndpoint reverseWebSocketEndpoint() {
		return new ReverseWebSocketEndpoint();
	}
}
