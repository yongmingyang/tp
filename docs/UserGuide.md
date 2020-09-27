## User Guide

Medmoriser helps medical students memorise and revise their content. It is optimized for CLI users so that frequent 
revisions can be done faster by typing in commands.


## Features

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a Q&A : `edit` [Coming Soon]

Edits an existing Q&A in the Medmoriser application.

### Locating questions by keywords: `find` [Coming Soon]

Finds Q&A whose questions contain any of the given keywords.

### Deleting a Q&A : `delete`

Deletes the specified Q&A from the question book.

Format: `delete INDEX`

* Deletes the Q&A at the specified `INDEX`.
* The index refers to the index number shown in the displayed question list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd Q&A in the question book.
* `find disease` followed by `delete 1` deletes the 1st question in the results of the `find` command.

### Viewing help : `help` [Coming soon]

Shows a message explaning how to access the help page.

Format: `help`

### Clearing all entries : `clear`

Clears all entries from the question book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Medmoriser data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Archiving data files [Coming Soon]

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | Coming Soon
**Find** | Coming Soon
**List** | `list`
**Help** | `help`
