package netgloo.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import netgloo.models.KeyStoreData;


@Transactional
public interface KeyStoreDataDao extends CrudRepository<KeyStoreData, Integer> {
	
	public KeyStoreData findByKeyStoreType(String keyStoreType);
	public KeyStoreData findByKeyStoreFileName(String keyStoreFileName);
	public KeyStoreData findByKeyStoreId(Integer keyStoreId);

}
