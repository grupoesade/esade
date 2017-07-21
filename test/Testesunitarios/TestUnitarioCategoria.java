/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testesunitarios;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import modelo.Categoriaa;
import modelo.Professor;
import org.junit.Test;

/**
 *
 * @author Paulino Francisco
 */
public class TestUnitarioCategoria extends TestCase {
    
    public TestUnitarioCategoria(String testName) {
        super(testName);
    }
    
    @Test
    public void testeCategoria(){
        
        
        
        
        Categoriaa cater = new Categoriaa("666" );
        
        System.out.println("Resultado:"+cater.getDescricao());
    
    
    }
    
}
