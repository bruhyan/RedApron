///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package jsf.converter;
//
//import entity.Recipe;
//import java.util.List;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.convert.FacesConverter;
//import jsf.managedBean.categoryManagementManagedBean;
//
///**
// *
// * @author mdk12
// */
//@FacesConverter(value = "pickListConverter", forClass = Recipe.class)
//
//public class PickListConverter {
//
//    public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        long recipeId = Long.parseLong(value);
//        List<Recipe> recipes = categoryManagementManagedBean.class.;
//        for (AssetCategory group : groups) {
//            if (group.getCategoryId() == groupId) {
//                return group;
//            }
//        }
//        return null;
//    }
//
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        if (value == null) {
//            return "";
//        }
//
//        if (value instanceof String) {
//            return "";
//        }
//
//        if (value instanceof Recipe) {
//            Recipe recipe = (Recipe) value;
//
//            try {
//                return recipe.getRecipeId().toString();
//            } catch (Exception ex) {
//                throw new IllegalArgumentException("Invalid value");
//            }
//        } else {
//            throw new IllegalArgumentException("Invalid value");
//        }
//    }
//}
