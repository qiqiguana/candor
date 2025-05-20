package org.asphodel;

import java.util.Collection;

/**
 * <p>
 * @author sunwj
 * @version 0.1
 * @since 0.1
 * Date: Mar 25, 2007
 * Time: 8:13:59 PM
 * One repository house may contains several index repositoried.And one repository contains<br/>
 * several index files,which holds the data the index userd for Seacher.
 * the implementation of this interface should set the repository house at first.
 * </p>
 */
public interface RepositoryManager {

    /**
     * @return return the canonical path the repository house.
     */
    public String getRepositoryHouse();

    /**
     * change current repositoryHouse path
     * @param repositoryHousePath new repositoryHouse path
     * */
    public void changeRepositoryHouse(String repositoryHousePath);

    /**
     * create the repository.If it exist already,it just return the canonical Path.
     *
     * @return the canonical Path of the repository
     * @throws FtrException if there any ioException occurs.
     */
    public String createRepository(String repositoryIdentifier) throws FtrException;

    /**
     * delete the given repository,include all the index it contains
     */
    public void deleteRepository(String repositoryIdentifier) throws FtrException;

    /**
     * delete all the repositories under the repostoryHouse
     * */
    public void deleteAllRepositories() throws FtrException;

    /**
     * to detect whether a given repostiroy exist or not.
     * */
    public boolean exist(String repositoryIdentifier);


    /**
     * list all the repositories under the current repositoryHouse
     * @return the repositoriesIdentifier ,it never return null for convenience
     * */
    public Collection<String> getAllRepositories();
    /**
     * @param repositoryIdentifier dirname relative to repository home<br>
     *          if it is null,the default repository will be used 
     * */
    public void optimizeRepository(String repositoryIdentifier)throws FtrException;
}
