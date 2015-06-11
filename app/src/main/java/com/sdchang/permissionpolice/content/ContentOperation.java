package com.sdchang.permissionpolice.content;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build.VERSION_CODES;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Attendees;
import android.provider.CalendarContract.CalendarAlerts;
import android.provider.CalendarContract.CalendarCache;
import android.provider.CalendarContract.CalendarEntity;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Colors;
import android.provider.CalendarContract.EventDays;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.EventsEntity;
import android.provider.CalendarContract.ExtendedProperties;
import android.provider.CalendarContract.Instances;
import android.provider.CalendarContract.Reminders;
import android.provider.CalendarContract.SyncState;
import android.provider.ContactsContract.Contacts;
import android.provider.Telephony.Carriers;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import android.provider.Telephony.Threads;
import android.support.annotation.StringRes;
import com.sdchang.permissionpolice.R;

/**
 *
 */
@TargetApi(VERSION_CODES.LOLLIPOP)
class ContentOperation {

    // TODO #4: Parse other types of content provider URIs to show client intent to the user
    public static ContentOperation[] operations = new ContentOperation[]{
            // CalendarContract
            // TODO #23: Implement CalendarContract's static query methods in a request builder
            new ContentOperation(CalendarContract.CONTENT_URI, R.string.dialogTitle_calendarContentUri, 14),
            new ContentOperation(Attendees.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(CalendarAlerts.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(CalendarAlerts.CONTENT_URI_BY_INSTANCE, R.string
                    .dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(CalendarCache.URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(CalendarEntity.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Calendars.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Colors.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(EventDays.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Events.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Events.CONTENT_EXCEPTION_URI,
                    R.string.dialogTitle_calendarEventsContentExceptionUri, 14),
            new ContentOperation(EventsEntity.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(ExtendedProperties.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Instances.CONTENT_BY_DAY_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Instances.CONTENT_SEARCH_BY_DAY_URI, R.string.dialogTitle_calendarEventsContentUri,
                    14),
            new ContentOperation(Instances.CONTENT_SEARCH_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Instances.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(Reminders.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),
            new ContentOperation(SyncState.CONTENT_URI, R.string.dialogTitle_calendarEventsContentUri, 14),

            // ContactContract
            new ContentOperation(Contacts.CONTENT_FILTER_URI, R.string.dialogTitle_contactsContentFilterUri, 5),
            new ContentOperation(Contacts.CONTENT_GROUP_URI, R.string.dialogTitle_contactsContentGroupUri, 5),
            new ContentOperation(Contacts.CONTENT_LOOKUP_URI, R.string.dialogTitle_contactsContentLookupUri, 5),
            new ContentOperation(Contacts.CONTENT_STREQUENT_FILTER_URI,
                    R.string.dialogTitle_contactsContentStrequentFilterUri, 5),
            new ContentOperation(Contacts.CONTENT_STREQUENT_URI, R.string.dialogTitle_contactsContentStrequentUri, 5),
            new ContentOperation(Contacts.CONTENT_URI, R.string.dialogTitle_contactsContentUri, 5),
            new ContentOperation(Contacts.CONTENT_VCARD_URI, R.string.dialogTitle_contactsContentVcardUri, 5),
            new ContentOperation(Contacts.CONTENT_FREQUENT_URI, R.string.dialogTitle_contactsContentFilterUri, 21),
            new ContentOperation(Contacts.CONTENT_MULTI_VCARD_URI, R.string.dialogTitle_contactsContentMultiVcardUri,
                    21),

            // Telephony
            new ContentOperation(Carriers.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.REPORT_REQUEST_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.REPORT_STATUS_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.Draft.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.Inbox.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.Outbox.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.Rate.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Mms.Sent.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_CONVERSATIONS_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_DRAFT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_FILTER_BYPHONE_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_LOCKED_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_UNDELIVERED_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.SEARCH_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(MmsSms.PendingMessages.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.Conversations.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.Draft.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.Inbox.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.Outbox.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Sms.Sent.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Threads.CONTENT_URI, R.string.dialogTitle_telephonyContentUri, 19),
            new ContentOperation(Threads.OBSOLETE_THREADS_URI, R.string.dialogTitle_telephonyContentUri, 19),
    };

    public final Uri mUri;
    @StringRes public final int mDialogTitle;
    public final int mMinSdk;

    public ContentOperation(Uri uri, int dialogTitle, int minSdk) {
        mUri = uri;
        mDialogTitle = dialogTitle;
        mMinSdk = minSdk;
    }
}
