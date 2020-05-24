package net.softclear.admin.nomina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class NominaApplication implements CommandLineRunner {
	@Autowired
	private HolaMundo esteNombreNoEsRelevante;
	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(NominaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(esteNombreNoEsRelevante.saludar("Alejandro"));
		System.out.println(applicationContext.getBean(HolaMundo.class).saludar("Alejandro"));
	}
}

@Configuration
class MyConfiguration {
	@Bean
	// Por defecto, el nombre del método es el nombre del bean
	// Por defecto, todos los beans son singleton
	public Saludar saludarIngles() {
		return new SaludarIngles();
	}

	@Bean
	public HolaMundo holaMundo(@Qualifier("saludarIngles") Saludar saludar) {
		return new HolaMundo(saludar);
	}
}

//@Component
/**
 * - Component
 *   - Service (business logic)
 *   - Controller (mvC)
 *     - RestController (api rest controller)
 *   - Repository (dao)
 *   - Configuration (beans declaration)
 */
class HolaMundo {
	private final Saludar saludar;

	public HolaMundo(@Qualifier("saludarIngles") Saludar saludar) {
		this.saludar = saludar;
	}

	public String saludar(String nombre) {
		return saludar.hacer(nombre);
	}
}

interface Saludar {
	String hacer(String nombre);
}

@Component
class SaludarEspaniol implements Saludar {

	@Override
	public String hacer(String nombre) {
		return "Hola " + nombre;
	}
}

class SaludarIngles implements Saludar {

	@Override
	public String hacer(String nombre) {
		return "Hi " + nombre;
	}
}