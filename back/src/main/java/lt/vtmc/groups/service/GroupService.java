package lt.vtmc.groups.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lt.vtmc.docTypes.dao.DocTypeRepository;
import lt.vtmc.docTypes.model.DocType;
import lt.vtmc.groups.dao.GroupRepository;
import lt.vtmc.groups.dto.GroupDetailsDTO;
import lt.vtmc.groups.model.Group;
import lt.vtmc.user.dao.UserRepository;
import lt.vtmc.user.model.User;

/**
 * Group service for creating and managing groups.
 * 
 * @author VStoncius
 *
 */
@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DocTypeRepository docTypesRepo;

	/**
	 * 
	 * This method finds groups from group repository.
	 */
	public Group findGroupByName(String name) {
		return groupRepository.findGroupByName(name);
	}

	/**
	 * Method to create user groups.
	 * 
	 * @return Group
	 */
	@Transactional
	public Group createGroup(String name, String description) {
		Group newGroup = new Group();
		newGroup.setName(name);
		newGroup.setDescription(description);
		newGroup.setDocTypesToApprove(new ArrayList<DocType>());
		newGroup.setDocTypesToCreate(new ArrayList<DocType>());
		newGroup.setUserList(new ArrayList<User>());
		groupRepository.save(newGroup);
		return newGroup;

	}

	/**
	 * Method to add users to groups.
	 * 
	 * @param names
	 * @param username
	 */
	@Transactional
	public void addUserToGroupByUsername(String[] groupList, String username) {
		User userToAdd = userRepository.findUserByUsername(username);
		for (int i = 0; i < groupList.length; i++) {
			Group groupToAddTo = groupRepository.findGroupByName(groupList[i]);
			List<User> tmpUserList = groupToAddTo.getUserList();
			List<Group> tmpGroupList = userToAdd.getGroupList();
			if (tmpUserList.contains(userToAdd) == false && tmpGroupList.contains(groupToAddTo) == false) {
				tmpGroupList.add(groupToAddTo);
				userToAdd.setGroupList(tmpGroupList);
				tmpUserList.add(userToAdd);
				groupToAddTo.setUserList(tmpUserList);
			}
		}
	}

	/**
	 * Method to remove users from groups.
	 * 
	 * @param names
	 * @param username
	 */
	@Transactional
	public void removeUserFromGroupByUsername(String[] groupList, String username) {
		User userToRemove = userRepository.findUserByUsername(username);
		for (int i = 0; i < groupList.length; i++) {
			Group groupToRemoveFrom = groupRepository.findGroupByName(groupList[i]);
			List<User> tmpUserList = groupToRemoveFrom.getUserList();
			List<Group> tmpGroupList = userToRemove.getGroupList();
			if (tmpUserList.contains(userToRemove) == true && tmpGroupList.contains(groupToRemoveFrom) == true) {
				tmpGroupList.remove(groupToRemoveFrom);
				userToRemove.setGroupList(tmpGroupList);
				tmpUserList.remove(userToRemove);
				groupToRemoveFrom.setUserList(tmpUserList);
			}
		}
	}

	/**
	 * Method to add users to groups.
	 * 
	 * @param names
	 * @param username
	 */
	@Transactional
	public void removeUsersFromGroup(String groupname, String[] userlist) {
		Group groupToRemoveFrom = groupRepository.findGroupByName(groupname);
		for (int i = 0; i < userlist.length; i++) {
			User userToRemove = userRepository.findUserByUsername(userlist[i]);
			List<User> tmpUserList = groupToRemoveFrom.getUserList();
			List<Group> tmpGroupList = userToRemove.getGroupList();
			if (tmpUserList.contains(userToRemove) == true && tmpGroupList.contains(groupToRemoveFrom) == true) {
				tmpGroupList.add(groupToRemoveFrom);
				userToRemove.setGroupList(tmpGroupList);
				tmpUserList.add(userToRemove);
				groupToRemoveFrom.setUserList(tmpUserList);
			}
		}
	}

	@Transactional
	public void addUsersToGroup(String groupname, String[] userlist) {
		Group groupToAddTo = groupRepository.findGroupByName(groupname);
		for (int i = 0; i < userlist.length; i++) {
			User userToAdd = userRepository.findUserByUsername(userlist[i]);
			List<User> tmpUserList = groupToAddTo.getUserList();
			List<Group> tmpGroupList = userToAdd.getGroupList();
			if (tmpUserList.contains(userToAdd) == false && tmpGroupList.contains(groupToAddTo) == false) {
				tmpGroupList.add(groupToAddTo);
				userToAdd.setGroupList(tmpGroupList);
				tmpUserList.add(userToAdd);
				groupToAddTo.setUserList(tmpUserList);
			}
		}
	}

	public List<GroupDetailsDTO> retrieveAllGroups() {
		List<Group> grouplist = groupRepository.findAll();
		List<GroupDetailsDTO> allGroupss = new ArrayList<GroupDetailsDTO>();
		for (int i = 0; i < grouplist.size(); i++) {
			allGroupss.add(new GroupDetailsDTO(grouplist.get(i)));
		}
		return allGroupss;
	}

	public void compareGroups(String[] newGroupList, String username) {
		List<Group> currentGroupList = new ArrayList<Group>();
		for (int i = 0; i < newGroupList.length; i++) {
			currentGroupList.add(groupRepository.findGroupByName(newGroupList[i]));
		}
		User tmpUser = userRepository.findUserByUsername(username);
		tmpUser.setGroupList(currentGroupList);
		userRepository.save(tmpUser);

//		***work in progress***
//		List<Group> currentGroupList = userRepository.findUserByUsername(username).getGroupList();
//		List<String> groupsToAdd = new ArrayList<String>();
//		for (int j = 0; j < newGroupList.length; j++) {
//			if (!currentGroupList.contains(groupRepository.findGroupByName(newGroupList[j]))){
//				groupsToAdd.add(newGroupList[j]);
//			};
//			if (currentGroupList.contains(groupRepository.findGroupByName(newGroupList[j]))){
//				currentGroupList.remove((groupRepository.findGroupByName(newGroupList[j])));
//		
//		groupRepository.save(groupToUpdate);	};
//		}
//		String[]groupsToRemove = new String[currentGroupList.size()];
//		for (int i = 0; i < currentGroupList.size(); i++) {
//			groupsToRemove[i] = currentGroupList.get(i).getName();
//		}
//		String[]groupsToAddString = new String[groupsToAdd.size()];
//		for (int i = 0; i < groupsToAdd.size(); i++) {
//			groupsToAddString[i] = groupsToAdd.get(i).toString();
//		}
//		addUserToGroupByUsername(groupsToAddString, username);
//		removeUserFromGroupByUsername(groupsToRemove, username);
	}
	@Transactional
	public void updateGroupDetails(String newName, String name, String description, String[] newUserList, String[] docTypesToApprove,
			String[] docTypesToCreate) {
		Group groupToUpdate = groupRepository.findGroupByName(name);
		List<User> currentUserList = new ArrayList<User>();
		groupToUpdate.setUserList(currentUserList);
		groupToUpdate.setDescription(description);
		groupRepository.save(groupToUpdate);
		groupToUpdate.setName(newName);
		for (int i = 0; i < newUserList.length; i++) {
			User userToAdd = userRepository.findUserByUsername(newUserList[i]);
			List<User> tmpUserList = groupToUpdate.getUserList();
			List<Group> tmpGroupList = userToAdd.getGroupList();
			if (tmpUserList.contains(userToAdd) == false && tmpGroupList.contains(groupToUpdate) == false) {
				tmpGroupList.add(groupToUpdate);
				userToAdd.setGroupList(tmpGroupList);
				tmpUserList.add(userToAdd);
				groupToUpdate.setUserList(tmpUserList);
			}
		}
		groupRepository.save(groupToUpdate);
	}
	
	@Transactional
	public void addDocTypes(String name, String[] docTypesToApprove,
			String[] docTypesToCreate) {
		Group groupToAddTo = groupRepository.findGroupByName(name);
		List<DocType> docTypesToCreateList = new ArrayList<DocType>();
		for (int i = 0; i < docTypesToCreate.length; i++) {
			docTypesToCreateList.add(docTypesRepo.findDocTypeByName(docTypesToCreate[i]));
		}
		groupToAddTo.setDocTypesToCreate(docTypesToCreateList);
		List<DocType> docTypesToSignList = new ArrayList<DocType>();
		for (int i = 0; i < docTypesToApprove.length; i++) {
			docTypesToSignList.add(docTypesRepo.findDocTypeByName(docTypesToApprove[i]));
		}
		groupToAddTo.setDocTypesToApprove(docTypesToSignList);
		groupRepository.save(groupToAddTo);
	}

	public void deleteGroup(Group tmpGroup) {
		groupRepository.delete(tmpGroup);
		}
		
}
