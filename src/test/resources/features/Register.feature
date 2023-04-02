Feature: Register

  Scenario Outline: Create a valid new user
    Given I have chosen a "<browser>" and logged in Mailchimp
    And I enter my "<email>"
    And I enter a "<username>"
    And I select a "<password>"
    When I click sign up
    Then My registration will be "<result>"

    Examples:
      | browser | email            | username                                                                                         | password        | result       |
      | edge  | lin@qq.com | zzzz     |Q!we1234   | yes |
      | edge  | er@qq.com | likeyouhaha | !Aqwe123 | yes    |
      #| chrome  | zazaaszpd@qq.com | vazzapox                                                                                        | !!WQabb12362d   | yes          |
      #| chrome  | itislong@qq.com  | lanndarnamnanndarnamnanndarnamnndarnamnanndarnamnanndarnamnanndarnamnannmnanndarnamnanndarnamnanndarnamn | !!WQdee13333d   | tooLong      |
      #| chrome  | xsntaken@qq.com  | clunlin                                                                                           | !!WQdee13333dvv | occupiedName |
      | edge  |                  | helloworld2023                                                                                   | !!WQdccv133d    | noEmail      |