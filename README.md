This is an application to track an inventory containing one or more items. Lists of up to 1024 items are supported, and they can be
saved in JSON, HTML, or TSV (tab-separated value) formats.

Items must have a name, serial number, and value. A name must be between 2 and 256 characters, inclusive, and should not contain HTML tags.
A value may be any positive number with two decimal values. A serial number must be in the format A-XXX-XXX-XXX, where A is a letter or a number
and X is a number only. All serial numbers of items must be unique, no two items may have the same serial number.

Items can be searched by serial number or by name. All items not matching the selected search term will be hidden. When performing a new search, the old
search will be cleared. The Clear Search button will also clear previous search results so that all items are visible.