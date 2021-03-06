package lt.vtmc.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.dto.UserDetailsDTO;
import lt.vtmc.user.model.User;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForAbstractMethod;

/**
 * User service class to create and manipulate user instaces.
 * 
 * @author pra-va
 *
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Will return User object based user found by
	 * {@link lt.vtmc.security.service.UserService.findUserByUsername(String)}.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User newUser = userRepository.findUserByUsername(username);
		if (newUser == null) {
			throw new UsernameNotFoundException(username + " not found.");
		} else {
			return new org.springframework.security.core.userdetails.User(newUser.getUsername(), newUser.getPassword(),
					AuthorityUtils.createAuthorityList(new String[] { "ROLE_" + newUser.getRole() }));
		}
	}

	/**
	 * 
	 * This method finds users from user repository.
	 */
	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/**
	 * Method to create users.
	 * 
	 * @param username
	 * @param name
	 * @param surname
	 * @param password
	 * @return User
	 */
	@Transactional
	public User createUser(String username, String name, String surname, String password) {
		User newUser = new User(username, name, surname, password, "USER");
		List<Group> tmpList = new ArrayList<Group>();
		newUser.setGroupList(tmpList);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(password));
		userRepository.save(newUser);
		return newUser;
	}

	/**
	 * Method to create system administrators.
	 * 
	 * @param username
	 * @param name
	 * @param surname
	 * @param password
	 * @return User
	 */
	@Transactional
	public User createSystemAdministrator(String username, String name, String surname, String password) {
		User newUser = new User(username, name, surname, password, "ADMIN");
		List<Group> tmpList = new ArrayList<Group>();
		newUser.setGroupList(tmpList);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		newUser.setPassword(encoder.encode(password));
		userRepository.save(newUser);
		return newUser;
	}

	/**
	 * Method to return all system users.
	 * 
	 */
	public List<UserDetailsDTO> retrieveAllUsers() {
		List<User> tmpList = userRepository.findAll();
		List<UserDetailsDTO> allUsers = new ArrayList<UserDetailsDTO>();
		for (int i = 0; i < tmpList.size(); i++) {
			allUsers.add(new UserDetailsDTO(tmpList.get(i)));
		}
		return allUsers;
	}

	/**
	 * Method to delete system users.
	 * 
	 * @param User
	 */
	@Transactional
	public void deleteUser(User user) {
		userRepository.delete(user);
	}

	/**
	 * Method to update system user details.
	 * 
	 * @param username
	 * @param name
	 * @param surname
	 * @param password
	 * @return User
	 */
	@Transactional
	public User updateUserDetails(String username, String name, String surname, String password, String role) {
		User updatedUser = userRepository.findUserByUsername(username);
		updatedUser.setName(name);
		updatedUser.setSurname(surname);
		if (!password.equals("") && password.length() > 7 && password.length() < 21) {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			updatedUser.setPassword(encoder.encode(password));
		}
		updatedUser.setRole(role);
		userRepository.save(updatedUser);
		return updatedUser;
	}

	public String[] getUserDocTypesToCreate(String username) {
		User tmpUser = userRepository.findUserByUsername(username);
		List<Group> tmpGroupList = tmpUser.getGroupList();
		List<DocType> tmpDocTypeList = new ArrayList<DocType>();
		for (int i = 0; i < tmpGroupList.size(); i++) {
			if (tmpGroupList.get(i).getDocTypesToCreate() != null) {
				for (int j = 0; j < tmpGroupList.get(i).getDocTypesToCreate().size(); j++) {
					tmpDocTypeList.add(tmpGroupList.get(i).getDocTypesToCreate().get(j));
				}
			}
		}
		String[] list = new String[tmpDocTypeList.size()];
		for (int i = 0; i < tmpDocTypeList.size(); i++) {
			list[i] = tmpDocTypeList.get(i).getName();
		}
		String [] uniqueList = Arrays.stream(list).distinct().toArray(String[]::new);
		return uniqueList;
	}

}
