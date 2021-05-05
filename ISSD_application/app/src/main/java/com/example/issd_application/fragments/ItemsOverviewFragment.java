package com.example.issd_application.fragments;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import com.example.issd_application.adapters.ItemsAdapter;
import com.example.issd_application.FilterManager;
import com.example.issd_application.R;
import com.example.issd_application.adapters.FilterAdapter;
import com.example.issd_application.models.BorrowedItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemsOverviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemsOverviewFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View currentView;
    View contentView;
    GridView gridViewItems;
    ImageButton cartButton;
    ImageButton filterButton;
    EditText editText;
    ItemsAdapter itemsAdapter;
    FilterAdapter filterAdapter;
    ArrayList<BorrowedItem> Items;
    ArrayList<BorrowedItem> filterItems;
    PopupWindow popWnd;
    Context context;
    ImageButton arrowDownTechnology, arrowDownCourse;
    RelativeLayout subMenuTechnology, subMenuCourse;
    CheckBox inStock, phones, tablets, laptops, chargers, cameras, robots, fis, prop, others;
    ConstraintLayout detailSearch;
    ListView filterLists;
    FilterManager filterManager;

    public ItemsOverviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ItemsOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemsOverviewFragment newInstance() {
        ItemsOverviewFragment fragment = new ItemsOverviewFragment();
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contentView.setVisibility(View.GONE);
        popWnd.dismiss();
        filterManager.categories.clear();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getContext();
        Items = new ArrayList<BorrowedItem>();
        filterItems = new ArrayList<BorrowedItem>();
        filterManager = FilterManager.singleton();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_items_overview, container, false);

        cartButton = currentView.findViewById(R.id.ibToCart);

        setToolbarNavigation();
        gridViewItems = currentView.findViewById(R.id.gridViewItems);
        filterLists = currentView.findViewById(R.id.listViewFilter);

        editText = currentView.findViewById(R.id.searchEdit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        Items.add(new BorrowedItem("Macbook Pro", "5/4/2021", 0, "Laptops", "Others"));
        Items.add(new BorrowedItem("iPad Air 2", "5/4/2021", 3, "Tablets", "Others"));
        Items.add(new BorrowedItem("USB-C Cable", "19/4/2021", 4, "Chargers", "Others"));
        Items.add(new BorrowedItem("Camera", "23/4/2021", 5, "Cameras", "Pro-P"));

        itemsAdapter = new ItemsAdapter(getContext(), R.layout.grid_item_products, Items);
        gridViewItems.setAdapter(itemsAdapter);

        filterAdapter = new FilterAdapter(getContext(), R.layout.list_item_filter);
        filterLists.setAdapter(filterAdapter);

        filterAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(filterManager.categories.size() == 0)
                {
                    itemsAdapter = new ItemsAdapter(getContext(), R.layout.grid_item_products, Items);
                } else {
                    updateFilteredItems();
                }
                gridViewItems.setAdapter(itemsAdapter);
            }
        });

        filterButton = currentView.findViewById(R.id.iconFilter);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategory();
            }
        });

        detailSearch = currentView.findViewById(R.id.detailSearch);
        initPopMenu();

        return currentView;
    }

    private int timeTechnology = 0;
    private int timeCourse = 0;

    private void initPopMenu(){
        popWnd = new PopupWindow(context);
        contentView = LayoutInflater.from(context).inflate(R.layout.filter_popup, null);
        popWnd.setContentView(contentView);
        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        inStock = contentView.findViewById(R.id.inStock);
        inStock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if (isChecked) {
                    filterManager.categories.add(getString(R.string.filter_in_stock));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_in_stock));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        arrowDownTechnology = contentView.findViewById(R.id.arrowDownTechnology);
        subMenuTechnology = contentView.findViewById(R.id.subMenuTechnology);
        phones = contentView.findViewById(R.id.phones);
        tablets = contentView.findViewById(R.id.tablets);
        laptops = contentView.findViewById(R.id.laptops);
        chargers = contentView.findViewById(R.id.chargers);
        cameras = contentView.findViewById(R.id.cameras);
        robots = contentView.findViewById(R.id.robots);

        phones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_phones));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_phones));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        tablets.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_tablets));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_tablets));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        laptops.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_laptops));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_laptops));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        chargers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_chargers));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_chargers));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        cameras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_cameras));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_cameras));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        robots.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if (isChecked) {
                       filterManager.categories.add(getString(R.string.filter_robots));
                   } else {
                       filterManager.categories.remove(getString(R.string.filter_robots));
                   }
                   filterAdapter.notifyDataSetChanged();
               }
           }
        );


        arrowDownCourse = contentView.findViewById(R.id.arrowDownCourse);
        subMenuCourse = contentView.findViewById(R.id.subMenuCourse);
        fis = contentView.findViewById(R.id.fis);
        prop = contentView.findViewById(R.id.prop);
        others = contentView.findViewById(R.id.others);

        fis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_fis));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_fis));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        prop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_prop));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_prop));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                  if (isChecked) {
                      filterManager.categories.add(getString(R.string.filter_others));
                  } else {
                      filterManager.categories.remove(getString(R.string.filter_others));
                  }
                  filterAdapter.notifyDataSetChanged();
              }
          }
        );

        arrowDownTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeTechnology++;
                if (timeTechnology % 2 == 1) {
                    arrowDownTechnology.setImageResource(R.drawable.ic_arrow_drop_up);
                    subMenuTechnology.setVisibility(View.VISIBLE);
                }else {
                    arrowDownTechnology.setImageResource(R.drawable.ic_arrow_drop_down);
                    subMenuTechnology.setVisibility(View.GONE);
                }
            }
        });

        arrowDownCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeCourse++;
                if (timeCourse % 2 == 1) {
                    arrowDownCourse.setImageResource(R.drawable.ic_arrow_drop_up);
                    subMenuCourse.setVisibility(View.VISIBLE);

                }else {
                    arrowDownCourse.setImageResource(R.drawable.ic_arrow_drop_down);
                    subMenuCourse.setVisibility(View.GONE);
                }
            }
        });
    }

    private int times = 0;
    public void showCategory() {
        setCheckedBoxes();
        times++;
        if (times % 2 == 1) {
            popWnd.showAsDropDown(filterButton);
        } else {
            if (popWnd.isShowing()) {
                if (filterManager.categories.size() > 0) {
                    updateFilteredItems();
                }else {
                    itemsAdapter = new ItemsAdapter(getContext(), R.layout.grid_item_products, Items);
                } gridViewItems.setAdapter(itemsAdapter);
                popWnd.dismiss();
            }
        }
    }

    public void updateFilteredItems() {
        filterItems.clear();
        for(BorrowedItem item: Items)
        {
            for(String category: filterManager.categories)
            {
                if ((category.equals(getString(R.string.filter_in_stock)))) {
                    if (item.inStock > 0) {
                        filterItems.add(item);
                    }
                } else if (item.technology.equals(category) || item.course.equals(category)) {
                    filterItems.add(item);
                }
            }
        }
        itemsAdapter = new ItemsAdapter(getContext(), R.layout.grid_item_products, filterItems);
    }

    public void setCheckedBoxes()
    {
        inStock.setChecked(filterManager.categories.contains(getString(R.string.filter_in_stock)));
        phones.setChecked(filterManager.categories.contains(getString(R.string.filter_phones)));
        tablets.setChecked(filterManager.categories.contains(getString(R.string.filter_tablets)));
        laptops.setChecked(filterManager.categories.contains(getString(R.string.filter_laptops)));
        chargers.setChecked(filterManager.categories.contains(getString(R.string.filter_chargers)));
        cameras.setChecked(filterManager.categories.contains(getString(R.string.filter_cameras)));
        robots.setChecked(filterManager.categories.contains(getString(R.string.filter_robots)));
        fis.setChecked(filterManager.categories.contains(getString(R.string.filter_fis)));
        prop.setChecked(filterManager.categories.contains(getString(R.string.filter_prop)));
        others.setChecked(filterManager.categories.contains(getString(R.string.filter_others)));
    }

    private void filter(String text) {
        ArrayList<BorrowedItem> filteredList = new ArrayList<>();
        for (BorrowedItem item : Items) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        itemsAdapter.filterList(filteredList);
    }

    public void setToolbarNavigation() {
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragment_container, new CartFragment());
                fragmentTransaction.commit();
            }
        });
    }
}