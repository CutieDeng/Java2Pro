# Report

## Chapter 1. Project File Structure

> C:.
> │  .gitignore
> │  Java2Pro.iml
> │  README.md
> │  report.md
> │
> ├─.idea
> │  │  .gitignore
> │  │  junitgenerator-prj-settings.xml
> │  │  misc.xml
> │  │  modules.xml
> │  │  runConfigurations.xml
> │  │  uiDesigner.xml
> │  │  vcs.xml
> │  │  workspace.xml
> │  │
> │  ├─inspectionProfiles
> │  │      Project_Default.xml
> │  │
> │  └─shelf
> ├─out
> │  ├─production
> │  │  └─Java2Pro
> │  │      │  Entrance$1.class
> │  │      │  Entrance.class
> │  │      │
> │  │      ├─data
> │  │      │      Data.class
> │  │      │
> │  │      ├─RowInfo
> │  │      │      FirstVersionRowAttempt$Row.class
> │  │      │      FirstVersionRowAttempt.class
> │  │      │      OtherTableRow.class
> │  │      │      Tmp.class
> │  │      │      TmpRow.class
> │  │      │
> │  │      ├─service
> │  │      │      DataService.class
> │  │      │      MenuBarService.class
> │  │      │      ServiceFactory.class
> │  │      │      ShortcutService.class
> │  │      │      TabGenerateService.class
> │  │      │      TabPaneService.class
> │  │      │      TipService.class
> │  │      │
> │  │      ├─serviceimplements
> │  │      │      AmuseTipServiceImpl.class
> │  │      │      ColSpecialDataServiceImpl.class
> │  │      │      HighDataServiceImpl.class
> │  │      │      NormalDataServiceImpl.class
> │  │      │      SimpleFactory.class
> │  │      │      SimpleMenuBarServiceImpl.class
> │  │      │      SimpleTabPaneServiceImpl.class
> │  │      │      SimpleTipServiceImpl.class
> │  │      │
> │  │      ├─tabsupply
> │  │      │      AbstractTabSupplyImpl$1.class
> │  │      │      AbstractTabSupplyImpl.class
> │  │      │      CovidLineAnimationSupplyImpl.class
> │  │      │      CovidLineChartTabSupplyImpl$1.class
> │  │      │      CovidLineChartTabSupplyImpl$2.class
> │  │      │      CovidLineChartTabSupplyImpl$3.class
> │  │      │      CovidLineChartTabSupplyImpl$4.class
> │  │      │      CovidLineChartTabSupplyImpl.class
> │  │      │      CovidTableTabSupplyImpl$1.class
> │  │      │      CovidTableTabSupplyImpl.class
> │  │      │      LocationBarChartTabSupplyImpl$1.class
> │  │      │      LocationBarChartTabSupplyImpl.class
> │  │      │      LocationPieTabSupplyImpl$1.class
> │  │      │      LocationPieTabSupplyImpl.class
> │  │      │      LocationTableTabSupplyImpl$1.class
> │  │      │      LocationTableTabSupplyImpl.class
> │  │      │      OtherTableDataImpl$1.class
> │  │      │      OtherTableDataImpl.class
> │  │      │      StandTabSupplyTool.class
> │  │      │
> │  │      ├─tool
> │  │      │      Col2RowController$SpecialOneColumnData.class
> │  │      │      Col2RowController.class
> │  │      │      Controller.class
> │  │      │      FileController.class
> │  │      │      Tool.class
> │  │      │
> │  │      └─util
> │  │              Holder.class
> │  │
> │  └─test
> │      └─Java2Pro
> ├─res
> │  ├─file
> │  │      owid-covid-data.csv
> │  │      time_series_covid19_confirmed_global.csv
> │  │
> │  ├─graph
> │  │      bar_race.gif
> │  │      linerace.gif
> │  │      piechart.gif
> │  │      zhuchart.gif
> │  │
> │  └─picture
> │          icon1.png
> │
> └─src
>     │  Entrance.java
>     │
>     ├─data
>     │      Data.java
>     │
>     ├─RowInfo
>     │      FirstVersionRowAttempt.java
>     │      OtherTableRow.java
>     │      Tmp.java
>     │      TmpRow.java
>     │
>     ├─service
>     │      DataService.java
>     │      MenuBarService.java
>     │      ServiceFactory.java
>     │      ShortcutService.java
>     │      TabGenerateService.java
>     │      TabPaneService.java
>     │      TipService.java
>     │
>     ├─serviceimplements
>     │      AmuseTipServiceImpl.java
>     │      ColSpecialDataServiceImpl.java
>     │      HighDataServiceImpl.java
>     │      NormalDataServiceImpl.java
>     │      SimpleFactory.java
>     │      SimpleMenuBarServiceImpl.java
>     │      SimpleTabPaneServiceImpl.java
>     │      SimpleTipServiceImpl.java
>     │
>     ├─tabsupply
>     │      AbstractTabSupplyImpl.java
>     │      CovidLineAnimationSupplyImpl.java
>     │      CovidLineChartTabSupplyImpl.java
>     │      CovidTableTabSupplyImpl.java
>     │      LocationBarChartTabSupplyImpl.java
>     │      LocationPieTabSupplyImpl.java
>     │      LocationTableTabSupplyImpl.java
>     │      OtherTableDataImpl.java
>     │      StandTabSupplyTool.java
>     │
>     ├─tool
>     │      Col2RowController.java
>     │      Controller.java
>     │      FileController.java
>     │      Tool.java
>     │
>     └─util
>             Holder.java

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

## Chapter 3. Program Demonstration

Start Frame:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\Start.png" alt="Start" style="zoom:25%;" />

Location Table:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\LocationTable.png" alt="LocationTable" style="zoom:25%;" />

Location Table after searching "OWID":

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\LocationTableSearch.png" alt="LocationTableSearch" style="zoom:25%;" />

Covid Table:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\CovidTable.png" alt="CovidTable" style="zoom:25%;" />

Covid Table after searching "China":

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\CovidTableSearch.png" alt="CovidTableSearch" style="zoom:25%;" />

Other Covid Confirmed Table(Another source):

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\OtherCovidTable.png" alt="OtherCovidTable" style="zoom:25%;" />

Other Covid Table after searching "2021-11-29":

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\OtherCovidTableSearch.png" alt="OtherCovidTableSearch" style="zoom:25%;" />

Location Bar Chart:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\LocationBarChart.png" alt="LocationBarChart" style="zoom:25%;" />

Covid Line Chart:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\CovidLineChart.png" alt="CovidLineChart" style="zoom:25%;" />

Covid Line Chart Animation:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\CovidLineChartAni.png" alt="CovidLineChartAni" style="zoom:25%;" />

Location Pie Chart:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\LocationPieChart.png" alt="LocationPieChart" style="zoom:25%;" />

To save this Covid Bar Chart:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\export.png" alt="export" style="zoom:25%;" />

The saved Bar Chart:

<img src="E:\SUSTech\Study\2021Fall\Java2\Pro\ScreenShot\exportLocationPieChart.png" alt="exportLocationPieChart" style="zoom:25%;" />