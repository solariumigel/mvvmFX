package de.saxsys.jfx.mvvm.viewloader;

import de.saxsys.jfx.mvvm.api.FxmlView;
import de.saxsys.jfx.mvvm.api.JavaView;
import de.saxsys.jfx.mvvm.api.ViewModel;
import de.saxsys.jfx.mvvm.base.view.View;
import net.jodah.typetools.TypeResolver;

import java.util.ResourceBundle;

/**
 * Fluent API for loading Views. This is basically a wrapper around the {@link de.saxsys.jfx.mvvm.viewloader.ViewLoader}
 * to get a better usable API. 
 * 
 * @author manuel.mauky 
 */
public class FluentViewLoader {

	/**
	 * This class is the builder step to load a java based view. 
	 * @param <ViewType>
	 * @param <ViewModelType>
	 */
	public static class JavaViewStep<ViewType extends JavaView<? extends ViewModelType>, ViewModelType extends ViewModel> {
		
		private Class<? extends ViewType> viewType;
		private ResourceBundle resourceBundle;

		JavaViewStep(Class<? extends ViewType> viewType){
			this.viewType = viewType;
		}

		/**
		 * @param resourceBundle the resource bundle that is used while loading the view.
		 * @return this instance of the builder step.
		 */
		public JavaViewStep<ViewType, ViewModelType> resourceBundle(ResourceBundle resourceBundle){
			this.resourceBundle = resourceBundle;
			return this;
		}

		/**
		 * The final step of the Fluent API. This method loads the view based on the given params. 
		 *
		 * @return a view tuple containing the loaded view.
		 */
		public ViewTuple<ViewType, ViewModelType> load(){
			ViewLoader viewLoader = new ViewLoader();
			
			return viewLoader.loadViewTuple(viewType, resourceBundle);
		}
	}

	/**
	 * This class is the builder step to load a fxml based view.
	 * @param <ViewType>
	 * @param <ViewModelType>
	 */
	public static class FxmlViewStep<ViewType extends FxmlView<? extends ViewModelType>, ViewModelType extends ViewModel> {

		private Class<? extends ViewType> viewType;
		private ResourceBundle resourceBundle;
		private Object root;
		private ViewType codeBehind;

		FxmlViewStep(Class<? extends ViewType> viewType){
			this.viewType = viewType;
		}

		/**
		 * @param resourceBundle the resource bundle that is used while loading the view.
		 * @return this instance of the builder step.
		 */
		public FxmlViewStep<ViewType, ViewModelType> resourceBundle(ResourceBundle resourceBundle){
			this.resourceBundle = resourceBundle;
			return this;
		}

		/**
		 * This param is used to define a JavaFX node that is used as the root element
		 * when loading the fxml file. 
		 * <br />
		 * 
		 * This can be useful when creating custom controls with the fx:root element. 
		 * 
		 * @param root the root element that is used to load the fxml file.
		 * @return this instance of the builder step.
		 */
		public FxmlViewStep<ViewType, ViewModelType> root(Object root){
			this.root = root;
			return this;
		}

		/**
		 * This param is used to define an existing instance of the codeBehind class that 
		 * is used instead of creating a new one while loading. 
		 * <br />
		 *
		 * This can be useful when creating custom controls with the fx:root element. 
		 *
		 * @param codeBehind the codeBehind instance that is used to load the fxml file.
		 * @return this instance of the builder step.
		 */
		public FxmlViewStep<ViewType, ViewModelType> codeBehind(ViewType codeBehind){
			this.codeBehind = codeBehind;
			return this;
		}

		/**
		 * The final step of the Fluent API. This method loads the view based on the given params. 
		 * 
		 * @return a view tuple containing the loaded view.
		 */
		public ViewTuple<ViewType, ViewModelType> load(){
			ViewLoader viewLoader = new ViewLoader();

			return viewLoader.loadViewTuple(viewType, resourceBundle, codeBehind, root);
		}
	}


	/**
	 * This method is the entry point of the Fluent API to load a java based view.
	 */
	public static <ViewType extends JavaView<? extends ViewModelType>, ViewModelType extends ViewModel>
		JavaViewStep<ViewType, ViewModelType> javaView(Class<? extends ViewType> viewType){
		return new JavaViewStep<>(viewType);
	}

	/**
	 * This method is the entry point of the Fluent API to load a fxml based View.
	 */
	public static <ViewType extends FxmlView<? extends ViewModelType>, ViewModelType extends ViewModel>
	FxmlViewStep<ViewType, ViewModelType> fxmlView(Class<? extends ViewType> viewType){
		return new FxmlViewStep<>(viewType);
	}
	
}
