package com.mediamonks.needToRemove;

import com.mediamonks.domain.AccountType;
import com.mediamonks.domain.Client;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.ClientRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by zhangchen on 16/6/1.
 */
@Transactional(propagation = Propagation.REQUIRED)
@Service("dataMocker")
public class DataMocker implements InitializingBean{
    @Autowired
    private ClientRepository clientRepository;
    @Override
    public void afterPropertiesSet() throws Exception {
        Client client = clientRepository.findByName("test");
        if(client == null){
            client = new Client();
            client.changeName("test");
            WeChatAccount weChatAccount = new WeChatAccount();
            weChatAccount.initial("wx8c57d4b45dc0cfdb", "389ee2ea3896608468e112b227a14fbc");
            weChatAccount.changeAccountType(AccountType.SERVICEACCOUNT);
            client.addWeChatAccount(weChatAccount);
            clientRepository.save(client);
        }
    }
}
