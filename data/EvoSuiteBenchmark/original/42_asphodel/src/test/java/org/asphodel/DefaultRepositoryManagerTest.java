package org.asphodel;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.util.Collection;

/**
 * @author sunwj
 * @since 0.1
 *        Date: Mar 23, 2007
 *        Time: 1:33:42 PM
 *        test the logic of Indexbasemanager
 *        <p/>
 *        repository the unit to hold a dataset of index data
 */
public class DefaultRepositoryManagerTest {
    RepositoryManager repositoryManager = null;

    @BeforeTest
    void initTest() {
        repositoryManager = new DefaultRepositoryManager();
    }

    @AfterTest
    void clearTest() {
        try {
            repositoryManager.deleteAllRepositories();
        } catch (FtrException e) {
            e.printStackTrace(); 
        }
    }


    @Test
    public void testSetIndexRepositoryHouse() throws Exception {

        String repositoryHouse = FtrTestUtil.PATH_REPOSITORY_HOUSE;
        String originalCanonicalPath = new File(repositoryHouse).getCanonicalPath();
        repositoryManager.changeRepositoryHouse(repositoryHouse);
        assertEquals(originalCanonicalPath, repositoryManager.getRepositoryHouse());
    }

    @Test
    public void testCreateRepository() throws Exception {
        String repositoryIdentifier = FtrTestUtil.DEFAULT_REPOSITORY_IDENTIFER;
        String originalCanonicalPath = this.repositoryManager.getRepositoryHouse();
        File repo = new File(originalCanonicalPath, repositoryIdentifier);
        String targetPath = null;
        if (!repo.exists()) {
        } else {
            repositoryManager.deleteRepository(repositoryIdentifier);
        }
        targetPath = repositoryManager.createRepository(repositoryIdentifier);
        assertEquals(repo.getCanonicalPath(), targetPath);

    }

    @Test
    public void testDeleteSpecifiedRepository() throws Exception {
        String repositoryIdentifier = null;
        try {
            repositoryIdentifier = FtrTestUtil.DEFAULT_REPOSITORY_IDENTIFER;
            if (!repositoryManager.exist(repositoryIdentifier)) {
                repositoryManager.createRepository(repositoryIdentifier);
            }
            repositoryManager.deleteRepository(repositoryIdentifier);
        } catch (FtrException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        assertEquals(false, repositoryManager.exist(repositoryIdentifier));
    }

    @Test
    public void testDeleteAllRepositories() throws Exception
    {
        for (int i = 0; i < 10; i++)
        {
            String repositoryIdentifier = "index" + i;
            repositoryManager.createRepository(repositoryIdentifier);
        }
        repositoryManager.deleteAllRepositories();

        assertEquals(false, repositoryManager.exist("index0"));
    }

    @Test
    public void testGetAllRepositories()
    {
        String originalRepositoryHouse = repositoryManager.getRepositoryHouse();
       repositoryManager.changeRepositoryHouse(FtrTestUtil.PATH_REPOSITORY_HOUSE);
       Collection <String> repositories=repositoryManager.getAllRepositories();
       for(String repository:repositories){
         System.out.println(repository);  
       }
        repositoryManager.changeRepositoryHouse(originalRepositoryHouse);
       assertEquals(1,repositories.size());
    }


    public void testOptimizeRepository() {

    }
}
