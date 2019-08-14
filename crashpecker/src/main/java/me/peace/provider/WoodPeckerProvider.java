package me.peace.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import me.peace.communication.ProcessConstant;
import me.peace.communication.ProcessCoordinator;

public class WoodPeckerProvider extends ContentProvider {

    private ProcessCoordinator coordinator;

    @Override
    public boolean onCreate() {
        coordinator = new ProcessCoordinator();
        return true;
    }

    
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,  @Nullable String[] projection,  @Nullable String selection,  @Nullable String[] selectionArgs,  @Nullable String sortOrder) {
        return null;
    }

    
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,  @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri,  @Nullable String selection,  @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri,  @Nullable ContentValues values,  @Nullable String selection,  @Nullable String[] selectionArgs) {
        return 0;
    }

    
    @Nullable
    @Override
    public Bundle call( @NonNull String method,  @Nullable String arg,  @Nullable Bundle extras) {
        if (ProcessConstant.METHOD_SAVE_SP.equals(method)) {
            Context context = getContext();
            coordinator.saveSharedPreferences(context,arg,extras);
        } else if (ProcessConstant.METHOD_LOAD_SP.equals(method)) {
            Context context = getContext();
            return coordinator.loadSharedPreferences(context,arg,extras);
        }
        return null;
    }
}
