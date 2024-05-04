# Weird Bug

In the update user profile page, the website will send an ajax 
requsts to the system once the user clicks on save button. However,
image is not displayed. 

## Findings 
### 1. Intellij IDEA issue 
After submitting the picture, the user require to open the IDE again to 
load the picture, which might be a bug in the IDE. This issue can be solved
if the user use VS Code instead of Intellij IDEA. (VS Code only require to 
reload the page only)