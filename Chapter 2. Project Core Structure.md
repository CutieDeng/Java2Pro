## Chapter 2. Project Core Structure

### Layer 1. Core Function Manager

This project defines a factory class to achieve all functions. The basic factory class defined three functions. *(Actually four, but one had been abandoned.)*

Interface `ServiceFactory` is defined with below codes: 

```java
public interface ServiceFactory {
  MenuBarService getMenuBarService();
  TipService getTipService();
  TabPaneService getTabPaneService();
  ShortcutService getShortcutService();
}
```

Then we distribute the three functions using three unrelated interface: `MenuBarService`, `TipService`, `TabPaneService`. 

### Layer 2. Menu bar Manager

`MenuBarService` is defined with quite a lot of codes: 

```java
public interface MenuBarService {
  void init(); 
  MenuBar getMenuBar();
  default boolean setExportOnAction(Consumer<Void> consumer) {
    return false;
  }
  // some similar default methods defined. 
}
```

The basic method `init` is used to initialize the menu bar. Though you can achieve this process in the constructor, but considering some order-related problems, this method is provided to override and avoid some inconformity. 

Method `getMenuBar` is just used to get the menu bar which this service create and manage. Obviously, it muse be invoked after `init`. 

Other with `default` methods are the optional binding methods. If you want to achieve the menu bar service, you can achieve some of them as you like, even none. Therefore, it's a fact that you can not change the menu bar structure when you're out of `MenuBarService`. It's a method to protect the framework to work. 

### Layer 2. Tip Manager

`TipService` is defined with these codes: 

```java
public interface TipService {
  void init();
  Node getTip();
  void setTipMessage(String information);
}
```

As `MenuBarService`, you can use `init` to initialize the related *JavaFX* component and `getTip` to get it. 

According to the type declare, it doesn't have constrains about the tip actual achievement. Please determine by yourself. 

Most importantly, method `setTipMessage` you can invoke to notify the tip component to show the sentences. **It supports concurrent situations.**

### Layer 2. Tab Manager

`TabPaneService` is the nearly most important service among all. 

It's defined as: 

```java
public interface TabPaneService {
  void init();
  TabPane getTabPane();
}
```

Though it's quite easy, it manager the tab pane which stores all information about data visualization. 

But how to create a tab to show what I want to find? 

Then we should talk about `TabGenerateService`. 

### Layer 3. Tab Generate Service

`TabGenerateService` is an interface to generate a tab. 

It's defined as: 

```java
public interface TabGenerateService {
  Tab supply(ServiceFactory factory);
}
```

As you know, invoking the method `supply` can get a tab with the argument *factory*. This argument would give the whole access of the framework, then the concrete tab generator author can according to it, and finish all functions he wants. 

