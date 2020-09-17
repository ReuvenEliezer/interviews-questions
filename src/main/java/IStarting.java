import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public interface IStarting extends ApplicationListener<ApplicationReadyEvent> {

}
