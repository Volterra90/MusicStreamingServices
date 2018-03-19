import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.musicstreaming.streaming.model.Contido;
import com.musicstreaming.streaming.model.Playlist;
import com.musicstreaming.streaming.service.ContidoCriteria;
import com.musicstreaming.streaming.service.ContidoService;
import com.musicstreaming.streaming.service.impl.ContidoServiceImpl;
import com.musicstreaming.streaming.util.ToStringUtil;

public class ContidoServiceTest {
	
	private ContidoService contidoService = null;
	private static Logger logger = LogManager.getLogger(UsuarioServiceTest.class.getName());

	public ContidoServiceTest() {
		contidoService = new ContidoServiceImpl();
	}
	
	protected void testFindById() {

		logger.info("Testing findById ...");
		// Test data
		Long id = 1L;

		try {

			Contido c = contidoService.findById(id);
			if (logger.isDebugEnabled()) {
				logger.debug("Contido: " +ToStringBuilder.reflectionToString(c));
			}
		} catch (Throwable t) {
			logger.error("id = " + id, t);
		}
		logger.info("Test testFindById finished.\n");

	}
	
	protected void testFindByCriteria() {
		logger.info("Testing findbyCriteria ...");
		// Test data:		
		int pageSize = 2;
		ContidoCriteria cc = new ContidoCriteria();
		cc.setTipos(new Character [] {'C'});
		
		
		try {

			List<Contido> results = null;
			int startIndex = 1; 
			int total = 0;
			
			do {
				results = contidoService.findByCriteria(cc, startIndex, pageSize);
				if (results.size()>0) {
					logger.info("Page ["+startIndex+" - "+(startIndex+results.size()-1)+"] : ");				
					for (Contido c: results) {
						total++;
						logger.info("Result "+total+": "+ToStringUtil.toString(c));
					}
					startIndex = startIndex + pageSize;
				}
				
			} while (results.size()==pageSize);
			
			logger.info("Found "+total+" results.");
						
		} catch (Throwable t) {
			logger.error(t.getMessage(),t);
		}
		logger.info("Test findByCriteria finished.\n");
	}
	
	protected void testVota() {
		logger.info("Testing vota ...");	

		// Test data:
		Long idUsuario = 9L;
		Long idContido = 1L;
		Integer nota = 9; 
		try {

			contidoService.vota(idUsuario, idContido, nota);

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test vota finished.\n");		
	}
	
	protected void testCreate() {
		logger.info("Testing create ...");	
		
		Playlist p = new Playlist();
		p.setCodArtista(1L);
		p.setCodEstilo(3L);
		p.setCodUsuario(9L);
		p.setNome("Playlist Pop");
		try {
		contidoService.create(p);
		}
		catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
		logger.info("Test create finished. \n ");
		
	}
	
	
	

	public static void main(String[] args) {
		
		ContidoServiceTest test = new ContidoServiceTest();
		test.testFindById();
		test.testFindByCriteria();
		test.testVota();
		test.testCreate();
	}

}
