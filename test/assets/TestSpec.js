var assert = require("assert");
describe("Test", function() {
  it("should say hello", function() {
    var main = require('./javascripts/main');
    assert.equal(main.name, "Bob");
  });
});
