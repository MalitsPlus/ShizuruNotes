package com.github.malitsplus.shizurunotes.ui.calendar

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.github.malitsplus.shizurunotes.R
import java.lang.StringBuilder

class CalendarFragment : Fragment() {

    companion object {
        const val CALENDAR_PROJECTION_IDX_ID = 0
        const val CALENDAR_PROJECTION_IDX_NAME = 1
        const val CALENDAR_PROJECTION_IDX_ACCOUNT_NAME = 2
        const val CALENDAR_PROJECTION_IDX_ACCOUNT_TYPE = 3
        const val CALENDAR_PROJECTION_IDX_CALENDAR_COLOR = 4
        const val CALENDAR_PROJECTION_IDX_CALENDAR_DISPLAY_NAME = 5
        const val CALENDAR_PROJECTION_IDX_CALENDAR_ACCESS_LEVEL = 6
        const val CALENDAR_PROJECTION_IDX_CALENDAR_TIME_ZONE = 7
        const val CALENDAR_PROJECTION_IDX_VISIBLE = 8
        const val CALENDAR_PROJECTION_IDX_SYNC_EVENTS = 9
        const val CALENDAR_PROJECTION_IDX_OWNER_ACCOUNT = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val projection = arrayOf(
            Calendars._ID,
            Calendars.NAME,
            Calendars.ACCOUNT_NAME,
            Calendars.ACCOUNT_TYPE,
            Calendars.CALENDAR_COLOR,
            Calendars.CALENDAR_DISPLAY_NAME,
            Calendars.CALENDAR_ACCESS_LEVEL,
            Calendars.CALENDAR_TIME_ZONE,
            Calendars.VISIBLE,
            Calendars.SYNC_EVENTS,
            Calendars.OWNER_ACCOUNT
        )

        val uri = Calendars.CONTENT_URI as Uri
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        val contentResolver = requireContext().contentResolver
        val cursor = if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            throw IllegalAccessException("No permission to Calendar.")
            //return
        } else {
            contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        }

        val sb = StringBuilder()

        while (cursor?.moveToNext() == true) {
            sb.append(cursor.getLong(0)).append(',')
                .append(cursor.getString(1)).append(',')
                .append(cursor.getString(2)).append(',')
                .append(cursor.getString(3)).append(',')
                .append(cursor.getString(4)).append(',')
                .append(cursor.getString(5)).append(',')
                .append(cursor.getString(6)).append(',')
                .append(cursor.getString(7)).append(',')
                .append(cursor.getString(8)).append(',')
                .append(cursor.getString(9)).append(',')
                .append(cursor.getString(10)).append(',')
                .append("\n")
        }
        sb.toString()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

}
