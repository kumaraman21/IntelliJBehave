Narrative:
Testing a search result size

Meta:
@Suite smoke testing
Scenario: open a url

Given Open <caret>url 'http://some.url/path'
When search for 'something'
Then check result list size is 10
