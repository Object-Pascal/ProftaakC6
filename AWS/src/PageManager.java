import java.util.ArrayList;
import java.util.Collection;

public class PageManager {
    private ArrayList<IPageBehaviour> pages;
    private int currentPageIndex;

    public PageManager(IPageBehaviour page)
    {
        this(new ArrayList<IPageBehaviour>() {{ add(page); }});
    }

    public PageManager(ArrayList<IPageBehaviour> pages) {
        this.pages = pages;
        currentPageIndex = 0;
    }

    public void showActivePage() {
        getCurrentPage().showPage();
    }

    public void nextPage() {
        currentPageIndex = currentPageIndex++ >= pages.size() - 1 ? 0 : currentPageIndex;
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
