package com.forgeessentials.api.permissions;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.permission.IPermissionProvider;
import net.minecraftforge.permission.PermissionLevel;

import com.forgeessentials.api.UserIdent;
import com.forgeessentials.commons.selections.WorldArea;
import com.forgeessentials.commons.selections.WorldPoint;

/**
 * {@link IPermissionProviderBase} is the primary access-point to the permissions-system.
 */
public interface IPermissionProviderBase extends IPermissionProvider
{

    // ---------------------------------------------------------------------------
    // -- Persistence
    // ---------------------------------------------------------------------------

    /**
     * Marks the permission storage as dirty, so it will be persisted as soon as possible.
     * 
     * @param registeredPermission
     */
    void setDirty(boolean registeredPermission);

    // ---------------------------------------------------------------------------
    // -- Permissions
    // ---------------------------------------------------------------------------

    /**
     * Converts a string permission into a boolean value
     * 
     * @param permissionValue
     * @return
     */
    public boolean checkBooleanPermission(String permissionValue);

    String getPermission(UserIdent ident, WorldPoint point, WorldArea area, List<String> groups, String permissionNode, boolean isProperty);

    /**
     * Checks a permission for a player
     * 
     * @param player
     * @param permissionNode
     */
    boolean checkPermission(EntityPlayer player, String permissionNode);

    /**
     * Gets a permission-property for a player
     * 
     * @param player
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getPermissionProperty(EntityPlayer player, String permissionNode);

    /**
     * Register a permission description
     * 
     * @param permissionNode
     * @param description
     *            Description for the permission. Description will be stored as "permissionNode.$desc"
     *            permission-property.
     */
    void registerPermissionDescription(String permissionNode, String description);

    /**
     * Get a permission description
     * 
     * @param permissionNode
     * @return
     */
    String getPermissionDescription(String permissionNode);

    @Override
    public void registerPermission(String permission, PermissionLevel level);

    /**
     * This is where permissions are registered with their default value. This function also allows to register a
     * description.
     * 
     * @param permissionNode
     * @param level
     *            Default level of the permission. This can be used to tell the underlying {@link IPermissionProvider}
     *            whether a player should be allowed to access this permission by default, or as operator only.
     * @param description
     *            Description for the permission.
     */
    void registerPermission(String permissionNode, PermissionLevel level, String description);

    /**
     * Registers a permission property
     * 
     * @param permissionNode
     * @param defaultValue
     */
    void registerPermissionProperty(String permissionNode, String defaultValue);

    /**
     * Registers a permission property
     * 
     * @param permissionNode
     * @param defaultValue
     * @param description
     */
    void registerPermissionProperty(String permissionNode, String defaultValue, String description);

    /**
     * Registers a permission property
     * 
     * @param permissionNode
     * @param defaultValue
     */
    void registerPermissionPropertyOp(String permissionNode, String defaultValue);

    /**
     * Registers a permission property
     * 
     * @param permissionNode
     * @param defaultValue
     * @param description
     */
    void registerPermissionPropertyOp(String permissionNode, String defaultValue, String description);

    // ---------------------------------------------------------------------------

    /**
     * Checks a permission for a player
     * 
     * @param ident
     * @param permissionNode
     */
    boolean checkUserPermission(UserIdent ident, String permissionNode);

    /**
     * Gets a permission-property for a player
     * 
     * @param ident
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getUserPermissionProperty(UserIdent ident, String permissionNode);

    /**
     * Gets a permission-property for a player as integer
     * 
     * @param ident
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    Integer getUserPermissionPropertyInt(UserIdent ident, String permissionNode);

    // ---------------------------------------------------------------------------

    /**
     * Checks a permission for a player at a certain position
     * 
     * @param ident
     * @param targetPoint
     * @param permissionNode
     */
    boolean checkUserPermission(UserIdent ident, WorldPoint targetPoint, String permissionNode);

    /**
     * Gets a permission-property for a player at a certain position
     * 
     * @param ident
     * @param targetPoint
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getUserPermissionProperty(UserIdent ident, WorldPoint targetPoint, String permissionNode);

    // ---------------------------------------------------------------------------

    /**
     * Checks a permission for a player in a certain area
     * 
     * @param ident
     * @param targetArea
     * @param permissionNode
     */
    boolean checkUserPermission(UserIdent ident, WorldArea targetArea, String permissionNode);

    /**
     * Gets a permission-property for a player in a certain area
     * 
     * @param ident
     * @param targetArea
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getUserPermissionProperty(UserIdent ident, WorldArea targetArea, String permissionNode);

    // ---------------------------------------------------------------------------

    /**
     * Gets a permission-property for the specified group
     * 
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getGroupPermissionProperty(String group, String permissionNode);

    /**
     * Gets a permission for the specified group
     * 
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    boolean checkGroupPermission(String group, String permissionNode);

    /**
     * Gets a permission-property for a group at a certain position
     * 
     * @param group
     * @param point
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getGroupPermissionProperty(String group, WorldPoint point, String permissionNode);

    /**
     * Checks a permission for a group at a certain position
     * 
     * @param group
     * @param point
     * @param permissionNode
     */
    boolean checkGroupPermission(String group, WorldPoint point, String permissionNode);

    // ---------------------------------------------------------------------------

    /**
     * Gets a global permission-property from the _ALL_ group
     * 
     * @param permissionNode
     * @return property, if it exists, null otherwise
     */
    String getGlobalPermissionProperty(String permissionNode);

    /**
     * Gets a global permission from the _ALL_ group
     * 
     * @param permissionNode
     * @return
     */
    boolean checkGlobalPermission(String permissionNode);

    // ---------------------------------------------------------------------------

    /**
     * Sets a player permission
     * 
     * @param ident
     * @param permissionNode
     * @param value
     */
    void setPlayerPermission(UserIdent ident, String permissionNode, boolean value);

    /**
     * Sets a player permission-property
     * 
     * @param ident
     * @param permissionNode
     * @param value
     */
    void setPlayerPermissionProperty(UserIdent ident, String permissionNode, String value);

    /**
     * Sets a group permission
     * 
     * @param group
     *            Group name
     * @param permissionNode
     * @param value
     */
    void setGroupPermission(String group, String permissionNode, boolean value);

    /**
     * Sets a group permission-property
     * 
     * @param group
     * @param permissionNode
     * @param value
     */
    void setGroupPermissionProperty(String group, String permissionNode, String value);

    // ---------------------------------------------------------------------------

    /**
     * Checks, if the specified group is a system-group
     * 
     * @param group
     * @return
     */
    boolean isSystemGroup(String group);

    /**
     * Checks, if a group exists
     * 
     * @param groupName
     * @return true, if the group exists
     */
    boolean groupExists(String groupName);

    /**
     * Create a group
     * 
     * @param groupName
     */
    boolean createGroup(String groupName);

    /**
     * Add a player to a group
     * 
     * @param ident
     * @param group
     */
    void addPlayerToGroup(UserIdent ident, String group);

    /**
     * Remove a player from a group
     * 
     * @param ident
     * @param group
     */
    void removePlayerFromGroup(UserIdent ident, String group);

    /**
     * Returns the highest-priority group the the player belongs to.
     * 
     * @param ident
     */
    String getPrimaryGroup(UserIdent ident);

    // ---------------------------------------------------------------------------

}