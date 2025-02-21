package com.mygym.crm.persistence.storages;

import java.util.Map;

public interface UserStorage<T,E>{

    Map<T,E> getStorage();
}
