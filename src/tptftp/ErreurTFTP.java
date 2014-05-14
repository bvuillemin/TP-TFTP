/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tptftp;

/**
 *
 * @author Dimitri
 */
public class ErreurTFTP extends Exception{
    private int errType;
    private String errMsg;
    
    public ErreurTFTP(){
        super();
        
    }
}
