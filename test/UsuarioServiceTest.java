import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.model.Usuario;
import com.musicstreaming.streaming.service.UsuarioService;
import com.musicstreaming.streaming.service.impl.UsuarioServiceImpl;
import com.musicstreaming.streaming.util.PasswordEncryptionUtil;
import com.musicstreaming.streaming.util.ToStringUtil;

public class UsuarioServiceTest {

	private UsuarioService usuarioService = null;
	private static Logger logger = LogManager.getLogger(UsuarioServiceTest.class.getName());

	public UsuarioServiceTest() {
		usuarioService = new UsuarioServiceImpl();
	}

	protected void testFindById() {

		logger.info("Testing findById ...");
		// Test data
		String id = "Volterra";

		try {

			Usuario u = usuarioService.findUserById(id);
			if (logger.isDebugEnabled()) {
				logger.debug("Usuario: " +ToStringBuilder.reflectionToString(u));
			}
		} catch (Throwable t) {
			logger.error("id = " + id, t);
		}
		logger.info("Test testFindById finished.\n");

	}

	protected void testCreate() {
		logger.info("Testing create ...");

		// Test data:
		Usuario u = new Usuario();
		u.setApelidos("Taboada Varela");
		String plainPassword = "CrihtoGuapo92";
		u.setContrasinal(PasswordEncryptionUtil.encryptPassword(plainPassword));
		u.setEmail("topo_musero@hotmail.com");
		u.setFechaNacemento(new Date());
		u.setFechaSubscricion(new Date());
		u.setNick("Volterra_90");
		u.setNome("Alberto");
		u.setXenero('H');
		
		try {

			u = usuarioService.create(u);

			logger.info("Created: " + ToStringUtil.toString(u));

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test created finished.\n");
	}

	public static void main(String args[]) {
		UsuarioServiceTest test = new UsuarioServiceTest();
		test.testCreate();
		/*test.testFindById();*/
	}

}
