Feature: Register

  Scenario Outline: Create a valid new user
    Given I have chosen a "<browser>" and logged in Mailchimp
    And I enter my "<email>"
    And I enter a "<username>"
    And I select a "<password>"
    When I click sign up
    Then My registration will be "<result>"

    Examples:
      | browser | email             | username                                                                                               | password        | result       |
      | edge    | lazzain@qq.com    | zasaszzz                                                                                               | Q!we1234        | yes          |
      | edge    | aaer@qq.com       | liaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouha | !Aqwe123        | tooLong      |
      | edge    | xsntaken@qq.com   | clunlin                                                                                                | !!WQdee13333dvv | occupiedName |
      | edge    |                   | dkeie                                                                                                  | !!WQfrge13333   | noEmail      |
      | chrome  | zazaaaszpd@qq.com | vaaazzapox                                                                                             | !!WQabb12362d   | yes          |
      | chrome  | itislong@qq.com   | asdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloasdfghjkloqw | !!WQdee13333d   | tooLong      |
      | chrome  | xsntaken@qq.com   | clunlin                                                                                                | !!WQdee13333dvv | occupiedName |
      | chrome  |                   | helloworld2023                                                                                         | !!WQdccv133d    | noEmail      |