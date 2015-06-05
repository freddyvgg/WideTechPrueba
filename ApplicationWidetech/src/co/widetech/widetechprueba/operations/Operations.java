package co.widetech.widetechprueba.operations;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public abstract class Operations {
	
	public static final String OPERATIONS = "OPERATIONS";
	
	protected WeakReference<Activity> mActivity;
	
	protected SparseArray<ButtonCommand> mButtonCommands = new SparseArray<>();
	
	protected SparseArray<MenuCommand> mMenuCommands = new SparseArray<>();
	
	public Operations(Activity mActivity) {
		super();
		this.mActivity = new WeakReference<Activity>(mActivity);
	}

	public final Activity getActivity()
	{
		return mActivity.get();
	}
	
	public final void setActivity(Activity activity)
	{
		mActivity = new WeakReference<Activity>(activity);
	}
	
	public void putButtonCommand(int key, ButtonCommand value) {
		mButtonCommands.put(key, value);
	}
	
	public void putMenuCommand(int key, MenuCommand value) {
		mMenuCommands.put(key, value);
	}
	
	public abstract void onConfigurationChange(Activity activity);

	public void onButtonPressed(View view) {
		ButtonCommand buttonStrategy = mButtonCommands.get(view.getId());
		
		if(null!=buttonStrategy)
			buttonStrategy.execute(view);
	}
	
	public void onAttach(Activity activity)
	{
		//no-op
	}

	public void onBackPressed() {
		//no-op
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		//no-op
		
	}

	public boolean onContextItemSelected(MenuItem item) {
		MenuCommand MenuStrategy = mMenuCommands.get(item.getItemId());
		
		if(null!=MenuStrategy)
			MenuStrategy.execute(item);
		return false;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		MenuCommand MenuStrategy = mMenuCommands.get(item.getItemId());
		
		if(null!=MenuStrategy)
			MenuStrategy.execute(item);
		return false;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
	
	
	protected interface ButtonCommand {
		void execute(View view);
	}
	
	protected interface MenuCommand {
		void execute(MenuItem item);
	}

	

}
