import java.util.ArrayList;
import java.util.Collection;

public class PageManager {
    private ArrayList<IPageBehaviour> pages;
    private int currentPageIndex,lastShownPage;

    public PageManager(IPageBehaviour page)
    {
        this(new ArrayList<IPageBehaviour>() {{ add(page); }});
    }

    public PageManager(ArrayList<IPageBehaviour> pages) {
        this.pages = pages;
        currentPageIndex = 0;
        lastShownPage = -1;
    }

    public void showActivePage() {
        if(lastShownPage != currentPageIndex){
            DisplayManager.getInstance().clearScreen();
            getCurrentPage().showPage();
            lastShownPage = currentPageIndex;
        }
    }

    public void nextPage() {
        currentPageIndex = currentPageIndex++ >= pages.size() - 1 ? 0 : currentPageIndex;
    }
    public void previousPage() {
        currentPageIndex = currentPageIndex - 1 == -1 ? pages.size() - 1 : currentPageIndex - 1;
    }


    public void addPage(IPageBehaviour newPage) {
        pages.add(newPage);
    }

    public ArrayList<IPageBehaviour> getPages() {
        return pages;
    }

    public IPageBehaviour getCurrentPage() {
        return pages.get(currentPageIndex);
    }
}
