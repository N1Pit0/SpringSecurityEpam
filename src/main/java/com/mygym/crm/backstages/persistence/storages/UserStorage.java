package com.mygym.crm.backstages.persistence.storages;

import java.util.Map;

public interface UserStorage<T,E>{

    Map<T,E> getStorage();
}
