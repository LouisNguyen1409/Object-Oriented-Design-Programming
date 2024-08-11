package q8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class GitlabGroup implements GitlabPermissionsNode {
    private String name;
    private Map<User, PermissionsLevel> members = new HashMap<User, PermissionsLevel>();
    private List<GitlabPermissionsNode> subgroups = new ArrayList<GitlabPermissionsNode>();

    public GitlabGroup(String name, User creator) {
        this.name = name;
        members.put(creator, PermissionsLevel.OWNER);
    }

    public String getName() {
        return name;
    }

    public List<String> getUsersOfPermissionLevel(PermissionsLevel level) {
        Set<User> membersSet = members.keySet();
        List<String> names = new ArrayList<String>();
        for (User member : membersSet) {
            if (members.get(member).equals(level)) {
                names.add(member.getName());
            }
        }

        return names;
    }

    @Override
    public PermissionsLevel getUserPermissions(User user) {
        return members.get(user);
    }

    @Override
    public void updateUserPermissions(User userToUpdate, PermissionsLevel permissions, User updatingUser)
            throws GitlabAuthorisationException {
        authorise(updatingUser, PermissionsLevel.OWNER);

        members.put(userToUpdate, permissions);
    }

    @Override
    public GitlabGroup createSubgroup(String name, User user) throws GitlabAuthorisationException {
        authorise(user, PermissionsLevel.MAINTAINER);

        GitlabGroup group = new GitlabGroup(name, user);
        subgroups.add(group);
        return group;
    }

    @Override
    public GitlabProject createProject(String name, User user) throws GitlabAuthorisationException {
        authorise(user, PermissionsLevel.DEVELOPER);

        GitlabProject project = new GitlabProject(name, user);
        subgroups.add(project);
        return project;
    }

    private void authorise(User user, PermissionsLevel requiredPermissionsLevel) throws GitlabAuthorisationException {
        int perms = getUserPermissions(user).ordinal();
        int requiredPerms = requiredPermissionsLevel.ordinal();
        if (perms > requiredPerms) {
            throw new GitlabAuthorisationException("User is not authorised");
        }
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("type", "group");
        json.put("name", name);

        JSONArray subgroupJSON = new JSONArray(
                subgroups.stream()
                        .map(GitlabPermissionsNode::toJSON)
                        .collect(Collectors.toList()));

        json.put("subgroups", subgroupJSON);

        return json;
    }
}