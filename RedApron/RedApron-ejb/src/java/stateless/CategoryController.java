/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Category;
import entity.Recipe;
import exceptions.CategoryNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MX
 */
@Stateless
@Local(CategoryControllerLocal.class)
public class CategoryController implements CategoryControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    @Override
    public Category createNewCategory(Category category){
        em.persist(category);
        em.flush();
        return category;
    }
    
    @Override
    public Category retrieveCategoryById(long id) throws CategoryNotFoundException{
        Category category = em.find(Category.class, id);

        if (category != null) {
            return category;
        } else {
            throw new CategoryNotFoundException("Category does not exist!");
        }
    }
    
    @Override
    public Category retrieveCategoryByName(String name) throws CategoryNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.name = :inName");
        query.setParameter("inName", name);
        
        try
        {
            return (Category) query.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new CategoryNotFoundException("Category with name "+ name + " does not exist!");
        }
    }
    
    @Override
    public List<Category> retrieveAllCategories()
    {
        Query query = em.createQuery("SELECT c FROM Category c");
        
        return query.getResultList();
    }
    
    @Override
    public List<Recipe> retrieveRecipesByCategoryId(Long id) throws CategoryNotFoundException{
        Category category = retrieveCategoryById(id);
        Query query = em.createQuery("SELECT c FROM Recipe c WHERE c.categories.categoryId=:id");
        query.setParameter("id", id);
        
        return query.getResultList();
    }
    
    @Override
    public List<Recipe> retrieveRecipesByCategoryName(String name) throws CategoryNotFoundException{
        Category category = retrieveCategoryByName(name);
        Query query = em.createQuery("SELECT c FROM Recipe c WHERE c.categories.name=:name");
        query.setParameter("name", name);
        
        return query.getResultList();
    }
    
    @Override
    public List<Recipe> retrieveSubscPlansByCategoryId(Long id) throws CategoryNotFoundException{
        Category category = retrieveCategoryById(id);
        Query query = em.createQuery("SELECT c FROM SubscriptionPlan c WHERE c.catergory.categoryId=:id");
        query.setParameter("id", id);
        
        return query.getResultList();
    }
    
    @Override
    public List<Recipe> retrieveSubscPlansByCategoryName(String name) throws CategoryNotFoundException{
        Category category = retrieveCategoryByName(name);
        Query query = em.createQuery("SELECT c FROM SubscriptionPlan c WHERE c.catergory.name=:name");
        query.setParameter("name", name);
        
        return query.getResultList();
    }
    
    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException{
        Category category = retrieveCategoryById(id);
        em.remove(category);
    }
    
}
