package hook;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class Hook {

    @BeforeAll
    public static void setUpBeforeClass(){
        System.out.println("Configuração global antes de todos os testes");
    }

    @AfterAll
    public static void tearDownAfterClass(){
        System.out.println("Limpeza global antes de todos os testes");
    }

    @Before
    public void setUp(){
        System.out.println("Iniciando um novo cenário de testes...");
    }

    @After
    public void tearDown(){
        System.out.println("Finalizando o cenário de teste...");
    }
}
