
package android.databinding;
import de.avkterwey.popularmovies.BR;
@javax.annotation.Generated("Android Data Binding")
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 21;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case de.avkterwey.popularmovies.R.layout.review_item:
                    return de.avkterwey.popularmovies.databinding.ReviewItemBinding.bind(view, bindingComponent);
                case de.avkterwey.popularmovies.R.layout.activity_main:
                    return de.avkterwey.popularmovies.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case de.avkterwey.popularmovies.R.layout.activity_detail:
                    return de.avkterwey.popularmovies.databinding.ActivityDetailBinding.bind(view, bindingComponent);
                case de.avkterwey.popularmovies.R.layout.trailer_item:
                    return de.avkterwey.popularmovies.databinding.TrailerItemBinding.bind(view, bindingComponent);
                case de.avkterwey.popularmovies.R.layout.movie_item:
                    return de.avkterwey.popularmovies.databinding.MovieItemBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 101895846: {
                if(tag.equals("layout/review_item_0")) {
                    return de.avkterwey.popularmovies.R.layout.review_item;
                }
                break;
            }
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return de.avkterwey.popularmovies.R.layout.activity_main;
                }
                break;
            }
            case 257710925: {
                if(tag.equals("layout/activity_detail_0")) {
                    return de.avkterwey.popularmovies.R.layout.activity_detail;
                }
                break;
            }
            case -821275627: {
                if(tag.equals("layout/trailer_item_0")) {
                    return de.avkterwey.popularmovies.R.layout.trailer_item;
                }
                break;
            }
            case -129728328: {
                if(tag.equals("layout/movie_item_0")) {
                    return de.avkterwey.popularmovies.R.layout.movie_item;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"
            ,"item"
            ,"trailer"};
    }
}