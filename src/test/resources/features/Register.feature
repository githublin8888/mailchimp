Feature: Register

  Scenario Outline: Create a new user
    Given I have chosen a "<browser>" and logged in Mailchimp
    And I enter my "<email>"
    And I enter a "<username>"
    And I select a "<password>"
    When I click sign up
    Then My registration will be "<result>"

    Examples:
      | browser | email         | username | password        | result       |
       #| chrome  | xddaedin@qq.com | zaedrezz                                                                                               | Q!we1234        | yes          |
       #| chrome  | aaer@qq.com     | liaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouhahaliaakeyouha | !Aqwe123        | tooLong      |
      #| chrome  | ntaken@qq.com | clunlin  | !!WQdee13333dvv | occupiedName |
      | chrome  |               | Saga     | !!WQdee13333dvv | noEmail |