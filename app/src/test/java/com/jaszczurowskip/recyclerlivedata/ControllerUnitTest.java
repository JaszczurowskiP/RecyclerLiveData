package com.jaszczurowskip.recyclerlivedata;

import com.jaszczurowskip.recyclerlivedata.datasource.ListItemDAO;
import com.jaszczurowskip.recyclerlivedata.datasource.ListItem;
import com.jaszczurowskip.recyclerlivedata.logic.Controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {
    @Mock
    ListItemDAO dataSource;
    @Mock
    ViewInterface view;
    Controller controller;

    private static  final ListItem testItem = new ListItem( "6:30AM 06/01/2017",
            "Check out content like Fragmented Podcast to expose", 0);

    @Before
    public void setUpTest(){
        MockitoAnnotations.initMocks(this);
        controller = new Controller(view, dataSource);
    }

    @Test
    public void onGetListDataSuccesfull() throws Exception {
        ArrayList<ListItem> listOfData = new ArrayList<>();
        listOfData.add(testItem);

        Mockito.when(dataSource.getListItems()).thenReturn(listOfData);
        controller.getListDataSuccesfull();

        Mockito.verify(view).setUpAdapterAndView(listOfData);
    }

    @Test
    public void onListItemClicked(){
        controller.onListItemClick(testItem);

        Mockito.verify(view).startDetailActivity(
                testItem.getDateAndTime(),
                testItem.getMessage(),
                testItem.getColorResource(),
                viewRoot);
    }
}