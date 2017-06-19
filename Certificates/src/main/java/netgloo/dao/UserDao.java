package netgloo.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import netgloo.models.User;

@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
	public User findByEmail(String email);

}
