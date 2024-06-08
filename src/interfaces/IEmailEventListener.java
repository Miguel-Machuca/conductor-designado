/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import Utils.Email;
import java.util.List;

/**
 *
 * @author andre
 */
public interface IEmailEventListener {
     void onReceiveEmailEvent(List<Email> emails);
}
